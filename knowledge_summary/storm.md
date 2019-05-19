# storm

## `mysql`,`hadoop`,`storm`之间的关系

+ `mysql`:事务性系统，面临海量数据的尴尬
+ `hadoop`:离线批处理  
      - 每天将所有数据收集起来，第二天凌晨时候统一批量计算
      - 海量数据，分布式的存储，每个服务器存储一部分数据
      - 分布式的计算，每台服务器都会计算一部分数据
      - 最终的计算结果，汇总起来
+ `storm` : 实时计算
      - 每过来一条数据会立即计算处理

## `storm`特点

+ （1）支持各种实时类的项目场景：
      - 实时处理消息以及更新数据库，基于最基础的实时计算语义和API(实时数据处理邻域)
      - 对实时的数据流持续的进行查询或计算，同时将最新的计算结果持续的推送给客户端展示，同样基于最基础的实时计算语义和API(实时数据分析邻域)
      - 对耗时的查询进行并行化，基于DRPC，即分布式RPC调用，单表30天数据，并行化，每个进程查询一条数据，最后组装结果
+ （2）高度的可伸缩性：如果要扩容，直接加机器，调整storm计算作业的并行度就可以了，storm会自动部署更多的进程和线程到其他的机器上去，无缝快速扩容
+ （3）数据不丢失的保证：storm的消息可靠机制开启后，可以保证一条数据都不丢
+ （4）超强的健壮性：从历史经验来看，storm比hadoop,spark等大数据类系统，健壮的多的多，因为元数据全部放zookeeper，不在内存中，随便挂都不要紧
+ （5）使用的便捷性：核心语义非常的简单，开发起来效率很高

## `storm`集群架构以及核心概念

### `storm`的集群架构

+ 涉及storm组件和第三方软件
      - Nimbus,Supervisor,Worker,Executor,Task,Zookeeper

### `Nimbus`,`Supervisor`,`Worker`,`Task`,`Zookeeprer`之间的关系

+ Storm中各节点介绍
      - 主控节点和工作节点  
      Storm将每个节点分为主控节点和工作节点两种，其中主控节点只有一个，工作节点可以有多个。
      - `Nimbus` （进程)
      主控节点运行Nimbus守护进程，类似于Hadoop中的yarn，负责在集群中分发代码，对节点分配任务，并监视主机故障。
      - `Supervisor`（进程）  
      每个工作节点运行Supervisor守护进程，负责监听工作节点上已经分配的主机作业，启动和停止Nimbus已经分配的工作进程。  
      supervisor会定时从zookeeper获取拓补信息topologies、任务分配信息assignments及各类心跳信息，以此为依据进行任务分配。  
      在supervisor同步时，会根据新的任务分配情况来启动新的worker或者关闭旧的worker并进行负载均衡。
      - `Worker` （进程）  
      Worker是具体处理Spout/Bolt逻辑的进程，根据提交的拓扑中conf.setNumWorkers(3);定义分配每个拓扑对应的worker数量，Storm会在每个Worker上均匀分配任务，一个Worker只能执行一个topology，但是可以执行其中的多个任务线程。
      - `Executor`(线程)  
      1个worker进程执行的是1个topology的子集（注：不会出现1个worker为多个topology服务）。1个worker进程会启动1个或多个executor线程来执行1个topology的component(spout或bolt)。因此，1个运行中的topology就是由集群中多台物理机上的多个worker进程组成的。  
      executor是1个被worker进程启动的单独线程。每个executor只会运行1个topology的1个component(spout或bolt)的task（注：task可以是1个或多个，storm默认是1个component只生成1个task，executor线程里会在每次循环里顺序调用所有task实例）。  
      task是最终运行spout或bolt中代码的单元（注：1个task即为spout或bolt的1个实例，executor线程在执行期间会调用该task的nextTuple或execute方法）。topology启动后，1个component(spout或bolt)的task数目是固定不变的，但该component使用的executor线程数可以动态调整（例如：1个executor线程可以执行该component的1个或多个task实例）。这意味着，对于1个component存在这样的条件：#threads<=#tasks（即：线程数小于等于task数目）。默认情况下task的数目等于executor线程数目，即1个executor线程只运行1个task。
      - `Task`(任务)  
      任务是指Worker中每个Spout/Bolt线程，每个Spout和Bolt在集群中会执行许多任务，每个任务对应一个线程执行，可以通过TopologyBuilder类的setSpout()和setBolt()方法来设置每个Spout或者Bolt的并行度。

+ Storm的容错机制
      - Worker进程死亡  
      当仅有Worker进程死亡时，其主机上的Supervisor会尝试重启Worker进程，如果连续重启都失败，当超过一定的失败次数之后，Nimbus会在其他主机上重启Worker。
      当Supervisor死亡时，如果某个主机上的Worker死亡了，由于没有Supervisor，所以无法在本机重启Worker，但会在其他主机上重启Worker，当Supervisor重启以后，会将本机的Worker重启，而之间在其他主机上重启的Worker则会消失，例如之前node2有三个Worker，node3有三个Worker，当node2的Supervisor死亡并且kill掉一个Worker之，node3出现四个Worker，重启node2的Supervisor之后，node2会重启一个Worker，恢复成三个Worker，node3kill掉多余的一个Worker，也恢复成三个Worker。  
      当Nimbus死亡时，Worker也会继续执行，但是某个Worker死亡时不会像Supervisor死亡时安排到其他主机上执行，因此如果Worker全部死亡，则任务执行失败。  
      集群中的Worker是均匀分配到各节点上的，例如一个作业有三个Worker时，会在一个节点（例如node2）分配两个Worker，在一个节点（例如node3）分配一个Worker，当再启动一个需要三个Worker的作业时，会在node2分配一个Worker，在node3分配两个Worker。
      - Nimbus或者Supervisor进程死亡  
      Nimbus和Supervisor被设计成是快速失败且无状态的，他们的状态都保存在ZooKeeper或者磁盘上，如果这两个进程死亡，它们不会像Worker一样自动重启，但是集群上的作业仍然可以在Worker中运行，并且他们重启之后会像什么都没发生一样正常工作。
      - ZooKeeper停止  
      ZooKeeper的停止同样不会影响已有的作业运行，此时kill掉Worker以后过段时间仍会在本机重启一个Worker。
> 综上所述，只有Nimbus失败并且所有Worker都失败之后才会影响集群上的作业运行，除此之外Storm集群的容错机制可以保证作业运行的可靠性。

### `storm`的核心概念

+ Topology，Spout，Bolt，Tuple，Stream
+ `Topology`拓扑：  
      务虚的一个概念，一个拓扑涵盖数据源获取/生产+数据处理的所有的代码逻辑
+ `Spout`：  
      数据源的一个代码组件，就是我们可以实现一个spout接口，写一个java类，在这个spout代码中，我们可以自己尝试去数据源获取数据，比如说从kafka中消费数据
+ `Bolt`：  
      一个业务处理的代码组件，spout会将数据传送给bolt，各种bolt还可以串联成一个计算链条，java类实现了一个bolt接口  
      一堆spout+bolt，就会组成一个topology，就是一个拓扑，实时计算作业，spout+bolt，一个拓扑涵盖数据源获取/生产+数据处理的所有的代码逻辑，topology
+ `Tuple`：  
      就是一条数据，每条数据都会被封装在tuple中，在多个spout和bolt之间传递
+ `Stream`：  
      就是一个流，务虚的一个概念，抽象的概念，源源不断过来的tuple，就组成了一条数据流

### 并行度

+ 在Storm的术语中，并行性(`parallelism`)专门用于描述所谓的并行性提示(`parallelism_hint`)，这意味着组件的执行者`Executor`（线程）的初始数量
 Storm集群中实际运行拓扑的三个主要实体 `Worker` ， `Executor` ， `Task`  
 其中，`Worker`(工作进程)可以有1到多个`Executor`(执行者线程)，`Executor`(执行者线程)可以有1到多`Task`（任务）  
 `Worker` > `Executor` > `Task`  
 增加并行度，可以设置以上实体的数量  
 `Worker`:`config.setNumWorkers(workers)`  
 `Executor`: `builder.setSpout(id, spout, parallelism_hint);` / `builder.setBolt(id, bolt, parallelism_hint);`  
 `Task` : `boltDeclarer.setNumTasks(num);` : `topologyBuilder.setBolt("green-bolt", new GreenBolt(), 2).setNumTasks(4);`

### 流分组

+ 流分组告诉拓扑如何在两个组件之间发送元组。(A stream grouping tells a topology how to send tuples between two components)

~~~txt
1.Shuffle Grouping ：随机分组
2.Fields Grouping ：按字段分组
3.All grouping ：广播
4.Global grouping ：全局分组
5.None grouping ：不分组
6.Direct grouping ：直接分组 指定分组
7.Local or Shuffle Grouping : 本地随机分组
~~~

### storm 集群搭建

~~~txt
1. 下载storm安装包，解压，重命名，配置环境变量 ~/.bashrc
2. 修改storm配置文件 conf/storm.yaml
//配置zookeeper集群地址
storm.zookeeper.servers:
      - "192.168.57.101"
      - "node201"
      - "node202"
//配置nimbus所在机器的ip
nimbus.seeds: ["192.168.57.101"]
//配置storm本地目录
storm.local.dir: "/var/storm"
//配置工作节点可以启动多少个worker,一个端口代表一个worker
supervisor.slots.ports:
      - 6700
      - 6701
      - 6702
      - 6703
1. 启动storm集群和ui界面和查看日志
一个节点 ： bin/storm nimbus >/dev/null 2>&1 &
三个节点 ： bin/storm supervisor >/dev/null 2>&1 &
一个节点 ： bin/storm ui >/dev/null 2>&1 &
三个节点 ： bin/storm logviewer  >/dev/null 2>&1 &  
ui : http://node101:8080

4. 集群中提交拓扑()
bin/storm jar jar包所在路径 拓扑所在main类 [其他参数...]

5. 关闭拓扑
bin/storm kill 拓扑名称
~~~

### 问题

+ 提交到集群中的storm打包文件，需不需要导入完整依赖jar包

### 常见错误

+ 启动报错: multiple default.yaml resource  
      提交到集群中的jar包，有多个default.yaml
