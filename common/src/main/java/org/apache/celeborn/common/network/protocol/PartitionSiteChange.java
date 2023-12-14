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

package org.apache.celeborn.common.network.protocol;

import io.netty.buffer.ByteBuf;

public final class PartitionSiteChange extends RequestMessage {
  public long requestId;

  public final String shuffleKey;
  public final String partitionUniqueId;
  public final int partitionId;
  public final int partitionSiteId;

  public PartitionSiteChange(
      String shuffleKey, String partitionUniqueId, int partitionId, int partitionSiteId) {
    this(0L, shuffleKey, partitionUniqueId, partitionId, partitionSiteId);
  }

  private PartitionSiteChange(
      long requestId,
      String shuffleKey,
      String partitionUniqueId,
      int partitionId,
      int partitionSiteId) {
    this.requestId = requestId;
    this.shuffleKey = shuffleKey;
    this.partitionUniqueId = partitionUniqueId;
    this.partitionId = partitionId;
    this.partitionSiteId = partitionSiteId;
  }

  @Override
  public int encodedLength() {
    return 8
        + Encoders.Strings.encodedLength(shuffleKey)
        + Encoders.Strings.encodedLength(partitionUniqueId)
        + 4
        + 4;
  }

  @Override
  public void encode(ByteBuf buf) {
    buf.writeLong(requestId);
    Encoders.Strings.encode(buf, shuffleKey);
    Encoders.Strings.encode(buf, partitionUniqueId);
    buf.writeInt(partitionId);
    buf.writeInt(partitionSiteId);
  }

  @Override
  public Type type() {
    return Type.PARTITION_SITE_CHANGE;
  }
}
