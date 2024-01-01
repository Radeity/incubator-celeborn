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

package org.apache.celeborn.service.deploy.cluster

import java.io.ByteArrayOutputStream
import java.nio.charset.StandardCharsets
import java.util.concurrent.ConcurrentHashMap

import org.apache.commons.lang3.RandomStringUtils
import org.junit.Assert

import org.apache.celeborn.client.{LifecycleManager, ShuffleClientImpl}
import org.apache.celeborn.client.read.MetricsCallback
import org.apache.celeborn.common.CelebornConf
import org.apache.celeborn.common.identity.UserIdentifier
import org.apache.celeborn.common.rpc.RpcEndpointRef

trait GeoReadWriteTestBase extends ReadWriteTestBase {

  val masterPort1 = 19097
  val masterPort2 = 19098

  override def beforeAll(): Unit = {
    val masterHost = "localhost"
    val masterEndpoints = s"localhost:$masterPort1,localhost:$masterPort2"
    logInfo("test initialized, setup Geo-distributed Celeborn cluster")
    setUpGeodistributedCluster(masterHost, masterPort, masterEndpoints)
  }

  def testMultiSiteReadWrite(): Unit = {
    val APP = "app-1"
    val clientConf = new CelebornConf()
      .set(CelebornConf.MASTER_ENDPOINTS.key, s"localhost:$masterPort1,localhost:$masterPort2")
      .set(CelebornConf.CLIENT_PUSH_REPLICATE_ENABLED.key, "false")
      .set(CelebornConf.CLIENT_PUSH_BUFFER_MAX_SIZE.key, "256K")
      .set(CelebornConf.READ_LOCAL_SHUFFLE_FILE, false)
      .set("celeborn.data.io.numConnectionsPerPeer", "1")
      .set("celeborn.client.shuffle.manager.port", "19001")
      .set(CelebornConf.GSS_MODE.key, "true")
      .set(CelebornConf.TEST_GSS_EARLY_SCHEDULE.key, "true")

    val shuffleMapperAttempts = new ConcurrentHashMap[Integer, Array[Int]]
    val rpcEndpointRefs: Array[RpcEndpointRef] = new Array(2)
    val siteLifecycleManager: Array[LifecycleManager] = Array(
      new LifecycleManager(APP, clientConf, 0, shuffleMapperAttempts, rpcEndpointRefs),
      new LifecycleManager(APP, clientConf, 1, shuffleMapperAttempts, rpcEndpointRefs))

    val site1ShuffleClient = new ShuffleClientImpl(APP, clientConf, UserIdentifier("mock", "mock"))
    site1ShuffleClient.setupLifecycleManagerRef(siteLifecycleManager(0).self)
    val site2ShuffleClient = new ShuffleClientImpl(APP, clientConf, UserIdentifier("mock", "mock"))
    site2ShuffleClient.setupLifecycleManagerRef(siteLifecycleManager(1).self)

    val appShuffleId = 16
    val shuffleId = site1ShuffleClient.getShuffleId(appShuffleId, "16-0-0", true)
    val shuffleId2 = site2ShuffleClient.getShuffleId(appShuffleId, "16-0-0", true)

    val site1Locs = site1ShuffleClient.getPartitionLocation(shuffleId, 3, 2)
    val site2Locs = site2ShuffleClient.getPartitionLocation(shuffleId, 3, 2)

    val dataPrefix = Array("000000", "111111", "222222", "333333")

    // data push of Map partition 1 (in site 1)
    val STR1 = dataPrefix(0) + RandomStringUtils.random(1920)
    val DATA1 = STR1.getBytes(StandardCharsets.UTF_8)
    val OFFSET1 = 0
    val LENGTH1 = DATA1.length
    val dataSize1 = site1ShuffleClient.pushData(shuffleId, 0, 0, 0, DATA1, OFFSET1, LENGTH1, 3, 2)
    logInfo(s"Map task 0 in site1 push data size $dataSize1 to reduce partition 0")

    val STR2 = dataPrefix(1) + RandomStringUtils.random(1024)
    val DATA2 = STR2.getBytes(StandardCharsets.UTF_8)
    val OFFSET2 = 0
    val LENGTH2 = DATA2.length
    val dataSize2 = site1ShuffleClient.pushData(shuffleId, 0, 0, 1, DATA2, OFFSET2, LENGTH2, 3, 2)
    logInfo(s"Map task 0 in site1 push data size $dataSize2 to reduce partition 1")
//    Thread.sleep(1000)
    site1ShuffleClient.mapperEnd(shuffleId, 0, 0, 3)
    // ===================================================================================================
//    val newSitePartitionLocation = Array(site1Locs.get(0), site2Locs.get(1))
//    for (lifecycleManager <- siteLifecycleManager) {
//      lifecycleManager.updatePartitionSite(APP, appShuffleId, newSitePartitionLocation)
//    }
//    logInfo("Update partition site on all lifecycleManagers")
    // ===================================================================================================

    // data push of Map partition 2 (in site 2)
    val STR3 = dataPrefix(2) + RandomStringUtils.random(2320)
    val DATA3 = STR3.getBytes(StandardCharsets.UTF_8)
    val OFFSET3 = 0
    val LENGTH3 = DATA3.length
    val dataSize3 = site2ShuffleClient.pushData(shuffleId, 1, 0, 0, DATA3, OFFSET3, LENGTH3, 3, 2)
    logInfo(s"Map task 1 in site2 push data size $dataSize3 to reduce partition 0")
    site2ShuffleClient.mapperEnd(shuffleId, 1, 0, 3)
    // ===================================================================================================

    // data push of Map partition 3 (in site 1)
    val STR4 = dataPrefix(3) + RandomStringUtils.random(1920)
    val DATA4 = STR4.getBytes(StandardCharsets.UTF_8)
    val OFFSET4 = 0
    val LENGTH4 = DATA4.length
    val dataSize4 = site1ShuffleClient.pushData(shuffleId, 2, 0, 1, DATA4, OFFSET4, LENGTH4, 3, 2)
    logInfo(s"Map task 2 in site1 push data size $dataSize4 to reduce partition 1")
    site1ShuffleClient.mapperEnd(shuffleId, 2, 0, 3)
//    site2ShuffleClient.mapperEnd(shuffleId, 2, 0, 3)
    // ===================================================================================================

//    Thread.sleep(1000)
//    val newSitePartitionLocation = Array(site1Locs.get(0), site1Locs.get(1))
//    for (lifecycleManager <- siteLifecycleManager) {
//      lifecycleManager.handleUpdatePartitionSite(APP, shuffleId, newSitePartitionLocation, 1)
//    }
//    logInfo("Update partition site on all lifecycleManagers")
    // ===================================================================================================

    val metricsCallback = new MetricsCallback {
      override def incBytesRead(bytesWritten: Long): Unit = {}
      override def incReadTime(time: Long): Unit = {}
    }
    // read data in partition 0
    val inputStream1 =
      site1ShuffleClient.readPartition(shuffleId, 0, 0, 0, Integer.MAX_VALUE, metricsCallback)
    val outputStream1 = new ByteArrayOutputStream()
    var b1 = inputStream1.read()
    while (b1 != -1) {
      outputStream1.write(b1)
      b1 = inputStream1.read()
    }
    val readBytes1 = outputStream1.toByteArray
    logInfo(s"Fetch data size: ${readBytes1.length}, expected ${LENGTH1 + LENGTH3}")
    Assert.assertEquals(LENGTH1 + LENGTH3, readBytes1.length)

    // read data in partition 1
    val inputStream2 =
      site1ShuffleClient.readPartition(shuffleId, 1, 0, 0, Integer.MAX_VALUE, metricsCallback)
    val outputStream2 = new ByteArrayOutputStream()
    var b2 = inputStream2.read()
    while (b2 != -1) {
      outputStream2.write(b2)
      b2 = inputStream2.read()
    }
    val readBytes2 = outputStream2.toByteArray
    logInfo(s"Fetch data size: ${readBytes2.length}, expected ${LENGTH2 + LENGTH4}")
    Assert.assertEquals(LENGTH2 + LENGTH4, readBytes2.length)

    Thread.sleep(5000L)
    site1ShuffleClient.shutdown()
    site2ShuffleClient.shutdown()
    siteLifecycleManager(0).rpcEnv.shutdown()
    siteLifecycleManager(1).rpcEnv.shutdown()
  }

}
