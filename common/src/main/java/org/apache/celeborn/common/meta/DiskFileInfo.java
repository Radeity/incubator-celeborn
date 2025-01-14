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

package org.apache.celeborn.common.meta;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.celeborn.common.identity.UserIdentifier;
import org.apache.celeborn.common.protocol.StorageInfo;
import org.apache.celeborn.common.util.Utils;

/*
 * This class will describe shuffle files located on local disks, HDFS and other remote storage.
 * */
public class DiskFileInfo extends FileInfo {
  private static Logger logger = LoggerFactory.getLogger(DiskFileInfo.class);
  private final Set<Long> streams = ConcurrentHashMap.newKeySet();
  private String filePath;
  private StorageInfo.Type storageType;
  private volatile long bytesFlushed;

  public DiskFileInfo(
      UserIdentifier userIdentifier,
      boolean partitionSplitEnabled,
      FileMeta fileMeta,
      String filePath,
      StorageInfo.Type storageType) {
    super(userIdentifier, partitionSplitEnabled, fileMeta);
    this.filePath = filePath;
    this.storageType = storageType;
  }

  // only called when restore from pb or in UT
  public DiskFileInfo(
      UserIdentifier userIdentifier,
      boolean partitionSplitEnabled,
      FileMeta fileMeta,
      String filePath,
      long bytesFlushed) {
    super(userIdentifier, partitionSplitEnabled, fileMeta);
    this.filePath = filePath;
    this.storageType = StorageInfo.Type.HDD;
    this.bytesFlushed = bytesFlushed;
  }

  public DiskFileInfo(File file, UserIdentifier userIdentifier) {
    this(
        userIdentifier,
        true,
        new ReduceFileMeta(new ArrayList(Arrays.asList(0L))),
        file.getAbsolutePath(),
        StorageInfo.Type.HDD);
  }

  public DiskFileInfo(UserIdentifier userIdentifier, FileMeta fileMeta, String filePath) {
    super(userIdentifier, true, fileMeta);
    this.filePath = filePath;
    this.storageType = StorageInfo.Type.HDD;
  }

  public boolean addStream(long streamId) {
    ReduceFileMeta reduceFileMeta = (ReduceFileMeta) fileMeta;
    synchronized (reduceFileMeta.getSorted()) {
      if (reduceFileMeta.getSorted().get()) {
        return false;
      } else {
        streams.add(streamId);
        return true;
      }
    }
  }

  public void closeStream(long streamId) {
    ReduceFileMeta reduceFileMeta = (ReduceFileMeta) fileMeta;
    synchronized (reduceFileMeta.getSorted()) {
      streams.remove(streamId);
    }
  }

  public boolean isStreamsEmpty() {
    ReduceFileMeta reduceFileMeta = (ReduceFileMeta) fileMeta;
    synchronized (reduceFileMeta.getSorted()) {
      return streams.isEmpty();
    }
  }

  public long getFileLength() {
    return bytesFlushed;
  }

  public long updateBytesFlushed(long numBytes) {
    bytesFlushed += numBytes;
    return bytesFlushed;
  }

  public File getFile() {
    return new File(filePath);
  }

  public String getFilePath() {
    return filePath;
  }

  public String getSortedPath() {
    return Utils.getSortedFilePath(filePath);
  }

  public String getIndexPath() {
    return Utils.getIndexFilePath(filePath);
  }

  public Path getHdfsPath() {
    return new Path(filePath);
  }

  public Path getHdfsIndexPath() {
    return new Path(Utils.getIndexFilePath(filePath));
  }

  public Path getHdfsSortedPath() {
    return new Path(Utils.getSortedFilePath(filePath));
  }

  public Path getHdfsWriterSuccessPath() {
    return new Path(Utils.getWriteSuccessFilePath(filePath));
  }

  public Path getHdfsPeerWriterSuccessPath() {
    return new Path(Utils.getWriteSuccessFilePath(Utils.getPeerPath(filePath)));
  }

  public void deleteAllFiles(FileSystem hdfsFs) {
    if (isHdfs()) {
      try {
        hdfsFs.delete(getHdfsPath(), false);
        hdfsFs.delete(getHdfsWriterSuccessPath(), false);
        hdfsFs.delete(getHdfsIndexPath(), false);
        hdfsFs.delete(getHdfsSortedPath(), false);
      } catch (Exception e) {
        // ignore delete exceptions because some other workers might be deleting the directory
        logger.debug(
            "delete HDFS file {},{},{},{} failed {}",
            getHdfsPath(),
            getHdfsWriterSuccessPath(),
            getHdfsIndexPath(),
            getHdfsSortedPath(),
            e);
      }
    } else {
      getFile().delete();
      new File(getIndexPath()).delete();
      new File(getSortedPath()).delete();
    }
  }

  public String getMountPoint() {
    return ((MapFileMeta) fileMeta).getMountPoint();
  }

  public void setMountPoint(String mountPoint) {
    ((MapFileMeta) fileMeta).setMountPoint(mountPoint);
  }

  public long getBytesFlushed() {
    return bytesFlushed;
  }

  public boolean isHdfs() {
    return Utils.isHdfsPath(filePath);
  }
}
