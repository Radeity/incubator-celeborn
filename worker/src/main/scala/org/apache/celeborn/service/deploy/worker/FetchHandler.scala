/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.celeborn.service.deploy.worker

import java.io.{FileNotFoundException, IOException}
import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.nio.charset.StandardCharsets
import java.util
import java.util.concurrent.{CompletableFuture, ThreadPoolExecutor}
import java.util.concurrent.atomic.{AtomicBoolean, AtomicInteger}
import java.util.function.Consumer

import com.google.common.base.Throwables
import com.google.protobuf.GeneratedMessageV3
import io.netty.buffer.Unpooled
import io.netty.util.concurrent.{Future, GenericFutureListener}

import org.apache.celeborn.common.CelebornConf
import org.apache.celeborn.common.CelebornConf.MAX_CHUNKS_BEING_TRANSFERRED
import org.apache.celeborn.common.exception.CelebornIOException
import org.apache.celeborn.common.internal.Logging
import org.apache.celeborn.common.meta.{FileInfo, FileManagedBuffers}
import org.apache.celeborn.common.network.buffer.{NettyManagedBuffer, NioManagedBuffer}
import org.apache.celeborn.common.network.client.{RpcResponseCallback, TransportClient, TransportClientFactory}
import org.apache.celeborn.common.network.protocol._
import org.apache.celeborn.common.network.server.BaseMessageHandler
import org.apache.celeborn.common.network.util.{NettyUtils, TransportConf}
import org.apache.celeborn.common.protocol._
import org.apache.celeborn.common.util.{ExceptionUtils, FileChannelUtils, PbSerDeUtils, ThreadUtils, Utils}
import org.apache.celeborn.service.deploy.worker.storage.{ChunkStreamManager, CreditStreamManager, PartitionFilesSorter, StorageManager}

class FetchHandler(
    val conf: CelebornConf,
    val transportConf: TransportConf,
    val workerSource: WorkerSource)
  extends BaseMessageHandler with Logging {

  val chunkStreamManager = new ChunkStreamManager()
  val maxChunkBeingTransferred: Option[Long] = conf.shuffleIoMaxChunksBeingTransferred

  val creditStreamManager = new CreditStreamManager(
    conf.partitionReadBuffersMin,
    conf.partitionReadBuffersMax,
    conf.creditStreamThreadsPerMountpoint,
    conf.readBuffersToTriggerReadMin)
  var storageManager: StorageManager = _
  var partitionsSorter: PartitionFilesSorter = _
  var registered: AtomicBoolean = new AtomicBoolean(false)

  var redirectThreadPool: ThreadPoolExecutor = _
  var fetchMaxReqsInFlight: Int = conf.clientFetchMaxReqsInFlight
  val pushDataTimeout: Long = conf.pushDataTimeoutMs
  var pushClientFactory: TransportClientFactory = _
  var workerReplicateRandomConnectionEnabled: Boolean = _

  def init(worker: Worker): Unit = {

    workerSource.addGauge(WorkerSource.CREDIT_STREAM_COUNT) { () =>
      creditStreamManager.getStreamsCount
    }

    workerSource.addGauge(WorkerSource.ACTIVE_MAP_PARTITION_COUNT) { () =>
      creditStreamManager.getActiveMapPartitionCount
    }

    this.storageManager = worker.storageManager
    this.partitionsSorter = worker.partitionsSorter
    this.registered = worker.registered
    this.pushClientFactory = worker.pushClientFactory
    this.redirectThreadPool = worker.replicateThreadPool
    this.workerReplicateRandomConnectionEnabled = worker.conf.workerReplicateRandomConnectionEnabled
  }

  def getRawFileInfo(
      shuffleKey: String,
      fileName: String): FileInfo = {
    // find FileWriter responsible for the data
    val fileInfo = storageManager.getFileInfo(shuffleKey, fileName)
    if (fileInfo == null) {
      val errMsg = s"Could not find file $fileName for $shuffleKey."
      logWarning(errMsg)
      throw new FileNotFoundException(errMsg)
    }
    fileInfo
  }

  override def receive(client: TransportClient, msg: RequestMessage): Unit = {
    msg match {
      case r: BufferStreamEnd =>
        handleEndStreamFromClient(r.getStreamId)
      case r: ReadAddCredit =>
        handleReadAddCredit(r.getCredit, r.getStreamId)
      case r: ChunkFetchRequest =>
        handleChunkFetchRequest(client, r.streamChunkSlice, r)
      case r: RpcRequest =>
        handleRpcRequest(client, r)
      case unknown: RequestMessage =>
        throw new IllegalArgumentException(s"Unknown message type id: ${unknown.`type`.id}")
    }
  }

  private def handleRpcRequest(client: TransportClient, rpcRequest: RpcRequest): Unit = {
    try {
      var message: GeneratedMessageV3 = null
      try {
        message = TransportMessage.fromByteBuffer(rpcRequest.body().nioByteBuffer())
          .getParsedPayload[GeneratedMessageV3]
      } catch {
        case exception: CelebornIOException =>
          logWarning("Handle request with legacy RPCs", exception)
          return handleLegacyRpcMessage(client, rpcRequest)
      }
      message match {
        case openStream: PbOpenStream =>
          handleOpenStreamInternal(
            client,
            openStream.getShuffleKey,
            openStream.getFileName,
            openStream.getStartIndex,
            openStream.getEndIndex,
            openStream.getInitialCredit,
            rpcRequest.requestId,
            isLegacy = false,
            openStream.getReadLocalShuffle)
        case bufferStreamEnd: PbBufferStreamEnd =>
          handleEndStreamFromClient(bufferStreamEnd.getStreamId, bufferStreamEnd.getStreamType)
        case readAddCredit: PbReadAddCredit =>
          handleReadAddCredit(readAddCredit.getCredit, readAddCredit.getStreamId)
        case chunkFetchRequest: PbChunkFetchRequest =>
          handleChunkFetchRequest(
            client,
            StreamChunkSlice.fromProto(chunkFetchRequest.getStreamChunkSlice),
            rpcRequest)
        case message: GeneratedMessageV3 =>
          logError(s"Unknown message $message")
      }
    } finally {
      rpcRequest.body().release()
    }
  }

  private def handleLegacyRpcMessage(client: TransportClient, rpcRequest: RpcRequest): Unit = {
    try {
      val message = Message.decode(rpcRequest.body().nioByteBuffer())
      message.`type`() match {
        case Message.Type.OPEN_STREAM =>
          val openStream = message.asInstanceOf[OpenStream]
          handleOpenStreamInternal(
            client,
            new String(openStream.shuffleKey, StandardCharsets.UTF_8),
            new String(openStream.fileName, StandardCharsets.UTF_8),
            openStream.startMapIndex,
            openStream.endMapIndex,
            initialCredit = 0,
            rpcRequestId = rpcRequest.requestId,
            isLegacy = true,
            // legacy [[OpenStream]] doesn't support read local shuffle
            readLocalShuffle = false)
        case Message.Type.OPEN_STREAM_WITH_CREDIT =>
          val openStreamWithCredit = message.asInstanceOf[OpenStreamWithCredit]
          handleOpenStreamInternal(
            client,
            new String(openStreamWithCredit.shuffleKey, StandardCharsets.UTF_8),
            new String(openStreamWithCredit.fileName, StandardCharsets.UTF_8),
            openStreamWithCredit.startIndex,
            openStreamWithCredit.endIndex,
            openStreamWithCredit.initialCredit,
            rpcRequestId = rpcRequest.requestId,
            isLegacy = true,
            readLocalShuffle = false)
        case _ =>
          logError(s"Received an unknown message type id: ${message.`type`.id}")
      }
    } catch {
      case e: Exception =>
        logError("Catch an error when handle legacy rpc message.", e)
    }

  }

  def handleFetchAndRedirect(
      shuffleKey: String,
      oldLoc: PartitionLocation,
      newLoc: PartitionLocation,
      startIndex: Int = 0,
      endIndex: Int = Int.MaxValue,
      readLocalShuffle: Boolean = true): Unit = {
    val fileName = oldLoc.getFileName
    var fileInfo = getRawFileInfo(shuffleKey, fileName)

    // First, open stream
    val streamId = chunkStreamManager.nextStreamId()
    if ((endIndex != Int.MaxValue) || (endIndex == Int.MaxValue && !fileInfo.addStream(
        streamId))) {
      fileInfo = partitionsSorter.getSortedFileInfo(
        shuffleKey,
        fileName,
        fileInfo,
        startIndex,
        endIndex)
    }

    val filePath = fileInfo.getFilePath
    val offsets = fileInfo.getChunkOffsets
    val numChunks = fileInfo.numChunks()
    // only consider read local reduce partition
    logDebug(s"Do fetch and redirect operation $shuffleKey $fileName $numChunks")

    val pbStreamHandlerBuilder =
      PbStreamHandler.newBuilder.setStreamId(streamId).setNumChunks(numChunks)
    if (offsets != null) {
      pbStreamHandlerBuilder.addAllChunkOffsets(offsets)
    }
    if (filePath.nonEmpty) {
      pbStreamHandlerBuilder.setFullPath(filePath)
    }
    val pbStreamHandler: PbStreamHandler = pbStreamHandlerBuilder.build()

    // Second, read local partition
    val shuffleChannel: FileChannel =
      FileChannelUtils.openReadableFileChannel(pbStreamHandler.getFullPath)
    var currentIndex = 0
    val future: CompletableFuture[Void] = new CompletableFuture[Void]()
    val counter: AtomicInteger = new AtomicInteger(numChunks)
    if (counter.get() == 0) {
      future.complete(null)
    }
    while (currentIndex < numChunks) {
      val toFetch: Int = Math.min(fetchMaxReqsInFlight, numChunks - currentIndex)
      val chunkIndex = currentIndex
      logDebug(s"[Partition-${newLoc.getUniqueId}] Trigger local reader fetch chunk with $chunkIndex and fetch $toFetch chunks")

      for (i <- 0 until toFetch) {
        val offset: Long = offsets.get(chunkIndex + i)
        val length: Long = offsets.get(chunkIndex + i + 1) - offset
        // A chunk must be smaller than INT.MAX_VALUE
        val buffer: ByteBuffer = ByteBuffer.allocate(length.toInt)
        while (buffer.hasRemaining) {
          // TODO: 这里的 read 操作加了锁，确认多个 chunk 时是否会有影响，以及是否有必要多线程？
          if (-1 == shuffleChannel.read(buffer))
            throw new CelebornIOException("Read local file " + filePath + " failed")
        }
        logDebug(
          s"[Partition-${newLoc.getUniqueId}] Read $chunkIndex offset $offset length $length")
        buffer.flip
        // Third, push local partition to new location
        val body = new NettyManagedBuffer(Unpooled.wrappedBuffer(buffer))
        body.retain()
        redirectThreadPool.submit(new Runnable {
          override def run(): Unit = {
            val callback: RpcResponseCallback = new RpcResponseCallback {
              override def onSuccess(response: ByteBuffer): Unit = {
                if (response.remaining() > 0) {
                  val resp = ByteBuffer.allocate(response.remaining())
                  resp.put(response)
                  resp.flip()
                }
                val currentCounter: Int = counter.decrementAndGet()
                logDebug(s"[Partition-${newLoc.getUniqueId}] Fetch and Redirect(send) chunk data to newLocation ${newLoc.getHost}:${newLoc.getPushPort} success, current counter: $currentCounter, response: $response")
                if (currentCounter == 0) {
                  future.complete(null)
                }
              }

              override def onFailure(e: Throwable): Unit = {
                logError(
                  s"Fetch and Redirect(send) chunk data to newLocation $newLoc failed, cause: ${e.getMessage}")
                future.completeExceptionally(e)
              }
            }

            try {
              val client: TransportClient =
                getClient(newLoc.getHost, newLoc.getPushPort, newLoc.getId)
              val newPushData: PushData = new PushData(
                0,
                shuffleKey,
                newLoc.getUniqueId,
                body)
              logDebug(
                s"[Partition-${newLoc.getUniqueId}] Start pushing index ${chunkIndex + i} to newLocation")
              client.pushData(newPushData, pushDataTimeout, callback)
            } catch {
              case e: Exception =>
                body.release()
                logError("Send local data to new site failed", e)
            }
          }
        })
      }
      currentIndex += toFetch
    }

    future.whenComplete((result, exception) => {
      if (exception == null) {
        // Fourth, close channel to avoid resource leak
        fileInfo.closeStream(streamId)
        shuffleChannel.close()

        // Fifth, send RPC message to new location to notify once-redirect steps done
        val (appUniqueId, shuffleId) = Utils.splitShuffleKey(shuffleKey)
        logDebug(s"[Partition-${newLoc.getUniqueId}] Send RPC message to new location to notify once-redirect steps done for shuffle $shuffleKey")
        val client: TransportClient = getClient(newLoc.getHost, newLoc.getPushPort, newLoc.getId)
        client.sendRpc(
          new TransportMessage(
            MessageType.PARTITION_SITE_CHANGE,
            PbPartitionSiteChange.newBuilder()
              .setApplicationId(appUniqueId)
              .setShuffleId(shuffleId)
              .addNewPartitionLocations(PbSerDeUtils.toPbPartitionLocation(newLoc))
              .build()
              .toByteArray).toByteBuffer)
//          conf.pushDataTimeoutMs)
        logDebug(s"[Partition-${newLoc.getUniqueId}] Receive resp for PARTITION_SITE_CHANGE done")
      }
    })
  }

  private def getClient(host: String, port: Int, partitionId: Int): TransportClient = {
    if (workerReplicateRandomConnectionEnabled) {
      pushClientFactory.createClient(host, port, -2)
    } else {
      pushClientFactory.createClient(host, port, partitionId)
    }
  }

  private def handleOpenStreamInternal(
      client: TransportClient,
      shuffleKey: String,
      fileName: String,
      startIndex: Int,
      endIndex: Int,
      initialCredit: Int,
      rpcRequestId: Long,
      isLegacy: Boolean,
      readLocalShuffle: Boolean = false): Unit = {
    workerSource.startTimer(WorkerSource.OPEN_STREAM_TIME, shuffleKey)
    try {
      var fileInfo = getRawFileInfo(shuffleKey, fileName)
      fileInfo.getPartitionType match {
        case PartitionType.REDUCE =>
          logDebug(s"Received open stream request $shuffleKey $fileName $startIndex " +
            s"$endIndex get file name $fileName from client channel " +
            s"${NettyUtils.getRemoteAddress(client.getChannel)}")

          val streamId = chunkStreamManager.nextStreamId()
          // we must get sorted fileInfo for the following cases.
          // 1. when the current request is a non-range openStream, but the original unsorted file
          //    has been deleted by another range's openStream request.
          // 2. when the current request is a range openStream request.
          if ((endIndex != Int.MaxValue) || (endIndex == Int.MaxValue && !fileInfo.addStream(
              streamId))) {
            fileInfo = partitionsSorter.getSortedFileInfo(
              shuffleKey,
              fileName,
              fileInfo,
              startIndex,
              endIndex)
          }
          if (readLocalShuffle) {
            replyStreamHandler(
              client,
              rpcRequestId,
              -1,
              fileInfo.numChunks(),
              isLegacy,
              fileInfo.getChunkOffsets,
              fileInfo.getFilePath)
          } else if (fileInfo.isHdfs) {
            replyStreamHandler(client, rpcRequestId, streamId, numChunks = 0, isLegacy)
          } else {
            val buffers = new FileManagedBuffers(fileInfo, transportConf)
            val fetchTimeMetrics = storageManager.getFetchTimeMetric(fileInfo.getFile)
            chunkStreamManager.registerStream(
              streamId,
              shuffleKey,
              buffers,
              fileName,
              fetchTimeMetrics)
            if (fileInfo.numChunks() == 0)
              logDebug(s"StreamId $streamId, fileName $fileName, mapRange " +
                s"[$startIndex-$endIndex] is empty. Received from client channel " +
                s"${NettyUtils.getRemoteAddress(client.getChannel)}")
            else logDebug(
              s"StreamId $streamId, fileName $fileName, numChunks ${fileInfo.numChunks()}, " +
                s"mapRange [$startIndex-$endIndex]. Received from client channel " +
                s"${NettyUtils.getRemoteAddress(client.getChannel)}")
            replyStreamHandler(client, rpcRequestId, streamId, fileInfo.numChunks(), isLegacy)
          }
        case PartitionType.MAP =>
          val creditStreamHandler =
            new Consumer[java.lang.Long] {
              override def accept(streamId: java.lang.Long): Unit = {
                replyStreamHandler(client, rpcRequestId, streamId, 0, isLegacy)
              }
            }

          creditStreamManager.registerStream(
            creditStreamHandler,
            client.getChannel,
            initialCredit,
            startIndex,
            endIndex,
            fileInfo)
        case PartitionType.MAPGROUP =>
      }
    } catch {
      case e: IOException =>
        handleRpcIOException(client, rpcRequestId, shuffleKey, fileName, e)
    } finally {
      workerSource.stopTimer(WorkerSource.OPEN_STREAM_TIME, shuffleKey)
    }
  }

  private def replyStreamHandler(
      client: TransportClient,
      requestId: Long,
      streamId: Long,
      numChunks: Int,
      isLegacy: Boolean,
      offsets: util.List[java.lang.Long] = null,
      filepath: String = ""): Unit = {
    if (isLegacy) {
      client.getChannel.writeAndFlush(new RpcResponse(
        requestId,
        new NioManagedBuffer(new StreamHandle(streamId, numChunks).toByteBuffer)))
    } else {
      val pbStreamHandlerBuilder = PbStreamHandler.newBuilder.setStreamId(streamId).setNumChunks(
        numChunks)
      if (offsets != null) {
        pbStreamHandlerBuilder.addAllChunkOffsets(offsets)
      }
      if (filepath.nonEmpty) {
        pbStreamHandlerBuilder.setFullPath(filepath)
      }
      val pbStreamHandler = pbStreamHandlerBuilder.build()
      client.getChannel.writeAndFlush(new RpcResponse(
        requestId,
        new NioManagedBuffer(new TransportMessage(
          MessageType.STREAM_HANDLER,
          pbStreamHandler.toByteArray).toByteBuffer)))
    }
  }

  private def handleRpcIOException(
      client: TransportClient,
      requestId: Long,
      shuffleKey: String,
      fileName: String,
      ioe: IOException): Unit = {
    // if open stream rpc failed, this IOException actually should be FileNotFoundException
    // we wrapper this IOException(Other place may have other exception like FileCorruptException) unify to
    // PartitionUnRetryableException for reader can give up this partition and choose to regenerate the partition data
    logError(
      s"Read file: $fileName with shuffleKey: $shuffleKey error from ${NettyUtils.getRemoteAddress(client.getChannel)}",
      ioe)
    handleRpcException(client, requestId, ioe)
  }

  private def handleRpcException(
      client: TransportClient,
      requestId: Long,
      ioe: IOException): Unit = {
    client.getChannel.writeAndFlush(new RpcFailure(
      requestId,
      Throwables.getStackTraceAsString(ExceptionUtils.wrapIOExceptionToUnRetryable(ioe))))
  }

  def handleEndStreamFromClient(streamId: Long): Unit = {
    handleEndStreamFromClient(streamId, StreamType.CreditStream)
  }

  def handleEndStreamFromClient(streamId: Long, streamType: StreamType): Unit = {
    streamType match {
      case StreamType.ChunkStream =>
        val (shuffleKey, fileName) = chunkStreamManager.getShuffleKeyAndFileName(streamId)
        getRawFileInfo(shuffleKey, fileName).closeStream(streamId)
      case StreamType.CreditStream =>
        creditStreamManager.notifyStreamEndByClient(streamId)
      case _ =>
        logError(s"Received a PbBufferStreamEnd message with unknown type $streamType")
    }
  }

  def handleReadAddCredit(credit: Int, streamId: Long): Unit = {
    creditStreamManager.addCredit(credit, streamId)
  }

  def handleChunkFetchRequest(
      client: TransportClient,
      streamChunkSlice: StreamChunkSlice,
      req: RequestMessage): Unit = {
    logDebug(s"Received req from ${NettyUtils.getRemoteAddress(client.getChannel)}" +
      s" to fetch block $streamChunkSlice")

    maxChunkBeingTransferred.foreach { threshold =>
      val chunksBeingTransferred = chunkStreamManager.chunksBeingTransferred // take high cpu usage
      if (chunksBeingTransferred > threshold) {
        val message = "Worker is too busy. The number of chunks being transferred " +
          s"$chunksBeingTransferred exceeds ${MAX_CHUNKS_BEING_TRANSFERRED.key} " +
          s"${Utils.bytesToString(threshold)}."
        logError(message)
        client.getChannel.writeAndFlush(new ChunkFetchFailure(streamChunkSlice, message))
        return
      }
    }

    workerSource.startTimer(WorkerSource.FETCH_CHUNK_TIME, req.toString)
    val fetchTimeMetric = chunkStreamManager.getFetchTimeMetric(streamChunkSlice.streamId)
    val fetchBeginTime = System.nanoTime()
    try {
      val buf = chunkStreamManager.getChunk(
        streamChunkSlice.streamId,
        streamChunkSlice.chunkIndex,
        streamChunkSlice.offset,
        streamChunkSlice.len)
      chunkStreamManager.chunkBeingSent(streamChunkSlice.streamId)
      client.getChannel.writeAndFlush(new ChunkFetchSuccess(streamChunkSlice, buf))
        .addListener(new GenericFutureListener[Future[_ >: Void]] {
          override def operationComplete(future: Future[_ >: Void]): Unit = {
            if (future.isSuccess) {
              if (log.isDebugEnabled) {
                logDebug(
                  s"Sending ChunkFetchSuccess operation succeeded, chunk $streamChunkSlice")
              }
            } else {
              logError(
                s"Sending ChunkFetchSuccess operation failed, chunk $streamChunkSlice",
                future.cause())
            }
            chunkStreamManager.chunkSent(streamChunkSlice.streamId)
            if (fetchTimeMetric != null) {
              fetchTimeMetric.update(System.nanoTime() - fetchBeginTime)
            }
            workerSource.stopTimer(WorkerSource.FETCH_CHUNK_TIME, req.toString)
          }
        })
    } catch {
      case e: Exception =>
        logError(
          s"Error opening block $streamChunkSlice for request from " +
            NettyUtils.getRemoteAddress(client.getChannel),
          e)
        client.getChannel.writeAndFlush(new ChunkFetchFailure(
          streamChunkSlice,
          Throwables.getStackTraceAsString(e)))
        workerSource.stopTimer(WorkerSource.FETCH_CHUNK_TIME, req.toString)
    }
  }

  override def checkRegistered: Boolean = registered.get

  /** Invoked when the channel associated with the given client is active. */
  override def channelActive(client: TransportClient): Unit = {
    logDebug(s"channel active ${client.getSocketAddress}")
    workerSource.incCounter(WorkerSource.ACTIVE_CONNECTION_COUNT)
    super.channelActive(client)
  }

  override def channelInactive(client: TransportClient): Unit = {
    workerSource.incCounter(WorkerSource.ACTIVE_CONNECTION_COUNT, -1)
    creditStreamManager.connectionTerminated(client.getChannel)
    logDebug(s"channel inactive ${client.getSocketAddress}")
  }

  override def exceptionCaught(cause: Throwable, client: TransportClient): Unit = {
    logWarning(s"exception caught ${client.getSocketAddress}", cause)
  }

  def cleanupExpiredShuffleKey(expiredShuffleKeys: util.HashSet[String]): Unit = {
    chunkStreamManager.cleanupExpiredShuffleKey(expiredShuffleKeys)
    // maybe null when running unit test.
    if (partitionsSorter != null) {
      partitionsSorter.cleanupExpiredShuffleKey(expiredShuffleKeys)
    }
  }

  def setPartitionsSorter(partitionFilesSorter: PartitionFilesSorter): Unit = {
    this.partitionsSorter = partitionFilesSorter
  }
}
