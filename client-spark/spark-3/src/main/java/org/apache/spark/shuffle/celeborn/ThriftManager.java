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

/**
 * Thrift client can not guarantee muli-thread safety, Thus, we use this manager to handle all write
 * request together
 */
package org.apache.spark.shuffle.celeborn;

import java.util.concurrent.LinkedBlockingQueue;

import org.apache.spark.shuffle.celeborn.rpc.Request2;
import org.apache.spark.shuffle.celeborn.rpc.gurobiService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;

public class ThriftManager {

  private Logger logger;

  private String applicationId;
  private TTransport transport;
  private gurobiService.Client thriftClient;

  private LinkedBlockingQueue<Request2> requestQueue;
  private Thread sendThread;
  private boolean close;

  public ThriftManager(String host, int port, Logger logger, String applicationId) {
    this.logger = logger;
    this.applicationId = applicationId;

    try {
      this.transport = new TSocket(host, port);
      this.transport.open();
    } catch (TTransportException e) {
      e.printStackTrace();
    }

    TProtocol protocol = new TBinaryProtocol(this.transport);
    this.thriftClient = new gurobiService.Client(protocol);

    this.requestQueue = new LinkedBlockingQueue<>();
    this.sendThread =
        new Thread(
            () -> {
              while (!this.close) {
                try {
                  while (!this.requestQueue.isEmpty()) {
                    Request2 request = this.requestQueue.take();
                    this.thriftClient.reportPartitionSize(request);
                    logger.info(
                        "report partition size for application {}, request: {}",
                        applicationId,
                        request);
                  }
                  Thread.sleep(150);
                } catch (InterruptedException e) {
                  e.printStackTrace();
                } catch (TException e) {
                  e.printStackTrace();
                }
              }
            });
    this.sendThread.setDaemon(true);
    this.sendThread.start();
    this.close = false;
  }

  public void send(Request2 request2) {
    requestQueue.add(request2);
  }

  public void stop() {
    if (null != transport) {
      this.transport.close();
    }
    this.close = true;
  }
}
