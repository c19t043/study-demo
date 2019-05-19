# kafka

## kafka集群搭建

scala，我就不想多说了，就是一门编程语言，现在比较火，很多比如大数据领域里面的spark（计算引擎）就是用scala编写的

将课程提供的scala-2.11.4.tgz使用WinSCP拷贝到/usr/local目录下。
对scala-2.11.4.tgz进行解压缩：tar -zxvf scala-2.11.4.tgz。
对scala目录进行重命名：mv scala-2.11.4 scala

配置scala相关的环境变量
vi ~/.bashrc
export SCALA_HOME=/usr/local/scala
export PATH=$SCALA_HOME/bin
source ~/.bashrc

查看scala是否安装成功：scala -version

按照上述步骤在其他机器上都安装好scala。使用scp将scala和.bashrc拷贝到另外两台机器上即可。

将课程提供的kafka_2.9.2-0.8.1.tgz使用WinSCP拷贝到/usr/local目录下。
对kafka_2.9.2-0.8.1.tgz进行解压缩：tar -zxvf kafka_2.9.2-0.8.1.tgz。
对kafka目录进行改名：mv kafka_2.9.2-0.8.1 kafka

配置kafka
vi /usr/local/kafka/config/server.properties
broker.id：依次增长的整数，0、1、2，集群中Broker的唯一id
zookeeper.connect=192.168.31.187:2181,192.168.31.19:2181,192.168.31.227:2181

安装slf4j
将课程提供的slf4j-1.7.6.zip上传到/usr/local目录下
unzip slf4j-1.7.6.zip
把slf4j中的slf4j-nop-1.7.6.jar复制到kafka的libs目录下面

解决kafka Unrecognized VM option 'UseCompressedOops'问题

vi /usr/local/kafka/bin/kafka-run-class.sh 

if [ -z "$KAFKA_JVM_PERFORMANCE_OPTS" ]; then
  KAFKA_JVM_PERFORMANCE_OPTS="-server  -XX:+UseCompressedOops -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+CMSClassUnloadingEnabled -XX:+CMSScavengeBeforeRemark -XX:+DisableExplicitGC -Djava.awt.headless=true"
fi

去掉-XX:+UseCompressedOops即可

按照上述步骤在另外两台机器分别安装kafka。用scp把kafka拷贝到其他机器即可。
唯一区别的，就是server.properties中的broker.id，要设置为1和2

在三台机器上的kafka目录下，分别执行以下命令：nohup bin/kafka-server-start.sh config/server.properties &

使用jps检查启动是否成功

使用基本命令检查kafka是否搭建成功

bin/kafka-topics.sh --zookeeper 192.168.31.187:2181,192.168.31.19:2181,192.168.31.227:2181 --topic test --replication-factor 1 --partitions 1 --create

bin/kafka-console-producer.sh --broker-list 192.168.31.181:9092,192.168.31.19:9092,192.168.31.227:9092 --topic test

bin/kafka-console-consumer.sh --zookeeper 192.168.31.187:2181,192.168.31.19:2181,192.168.31.227:2181 --topic test --from-beginning

## 为什么说Kafka使用磁盘比内存快（kafka大量使用磁盘缓存作为传统意义上的缓存）

1. 磁盘顺序读写不比内存读写慢，而且linux操作系统对磁盘读写做过很多优化，比如磁盘缓存，read-ahead,write-behind
2. 使用java做这些操作时，创建对象的代价比较高，随着内存中数据的增加,GC消息下降，而使用磁盘可以避免这些问题