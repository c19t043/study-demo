# rocketmq

## docker

```
docker pull foxiswho/rocketmq:server-4.4.0
docker pull foxiswho/rocketmq:broker-4.4.0
docker pull styletang/rocketmq-console-ng:1.0.0

#server
docker run --rm -d -p 9876:9876 --name rmqserver  foxiswho/rocketmq:server-4.4.0

#broker
docker run --rm -d -p 10911:10911 -p 10909:10909 \
--name rmqbroker \
--link rmqserver:namesrv \
-e "NAMESRV_ADDR=namesrv:9876" \
-e "JAVA_OPTS=-Duser.home=/opt"  \
-e "JAVA_OPT_EXT=-server -Xms128m -Xmx128m -Xmn128m" \
foxiswho/rocketmq:broker-4.4.0

#broker 配置文件位置 (容器内)
/etc/rocketmq/broker.conf

#使用自己的配置文件
#/User/fox/rmq/conf/broker.conf 为我的本地配置文件目录
#请全部复制到shell 中执行
docker run --rm  -d -p 10911:10911 -p 10909:10909 --name rmqbroker --link rmqserver:namesrv \
-e "NAMESRV_ADDR=namesrv:9876" \
-e "JAVA_OPTS=-Duser.home=/opt"  \
-e "JAVA_OPT_EXT=-server -Xms128m -Xmx128m -Xmn128m" \
-v /usr/local/app/broker.conf:/etc/rocketmq/broker.conf \
foxiswho/rocketmq:broker-4.4.0

#console
来自 https://hub.docker.com/r/styletang/rocketmq-console-ng/
docker run -d --rm --name rmqconsole --link rmqserver:namesrv -e "JAVA_OPTS=-Drocketmq.namesrv.addr=IP地址:9876 -Dcom.rocketmq.sendMessageWithVIPChannel=false" -p 8180:8080 -t styletang/rocketmq-console-ng:1.0.0

#Example:
docker run -d --rm --name rmqconsole --link rmqserver:namesrv \
-e "JAVA_OPTS=-Drocketmq.namesrv.addr=namesrv:9876 -Dcom.rocketmq.sendMessageWithVIPChannel=false" \
-p 8180:8080 \
styletang/rocketmq-console-ng:1.0.0

#浏览器访问
localhost:8180
```

## 自定义broker.conf
## 主要解决broker向namesrv注册ip，默认使用本地ip，外网访问不到，解决修改brokerIP1
```
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.

brokerIP1 = 192.168.57.101
brokerClusterName = DefaultCluster
brokerName = broker-a
brokerId = 0
deleteWhen = 04
fileReservedTime = 48
brokerRole = ASYNC_MASTER
flushDiskType = ASYNC_FLUSH
```