# redis

## redis的技术，包括4块

redis各种数据结构和命令的使用，包括java api的使用
redis一些特殊的解决方案的使用，pub/sub消息系统，分布式锁，输入的自动完成，等等
redis日常的管理相关的命令
redis企业级的集群部署和架构

### Redis中数据存储模式

+ Redis中数据存储模式有2种：cache-only,persistence;
+ cache-only即只做为“缓存”服务，不持久数据，数据在服务终止后将消失，此模式下也将不存在“数据恢复”的手段，是一种安全性低/效率高/容易扩展的方式；
+ persistence即为内存中的数据持久备份到磁盘文件，在服务重启后可以恢复，此模式下数据相对安全。

### redis为什么是单线程，redis这么支持高并发

## redis持久化

### redis持久化的意义

+ redis持久化的意义在于故障恢复  
1. 比如部署redis，提供缓存服务，也可以保存一些重要的数据，如果redis发生了灾难性故障，缓存中的数据就全部丢失了 
2. 如果将缓存中数据持久化到磁盘中，然后定期同步和备份，将备份发送到云存储上，当redis发生灾难性故障时，数据不会丢失全部，还可以从备份中恢复部分数据

### Redis提供了两种持久化方法

+ Redis DataBase(简称RDB)
+ Append-only file (简称AOF)

### redis持久化机制

+ redis有两种数据持久化方式，rdb和aof
+ rdb持久化机制，是对redis中数据执行周期性的持久化
+ aof持久化机制，是以每条写入指令为日志，通过append-only的模式写入一个日志文件中
+ 通过rdb或aof，都可以将redis内存中的数据持久化到磁盘中，然后将这些数据备份到别的地方，比如阿里云存储，如果redis发生故障，服务器上内存数据和磁盘上的数据都丢了，可以从云服务上拷贝会之前的数据，放到指定目录中，然后重新启动redis，redis就会根据持久化数据文件中的数据，去恢复内存中的数据，继续对外提供服务
+ 如果同时使用rdb和aof两种持久化机制，那么在redis重启时，会使用aof持久化文件来重新构建数据，因为一般情况下，aof中的数据更加完整

### rdb持久化优缺点

RDB是在某个时间点将数据写入一个临时文件，持久化结束后，用这个临时文件替换上次持久化的文件，达到数据恢复。 

+ 优点：使用单独子进程来进行持久化，主进程不会进行任何IO操作，保证了redis的高性能 
+ 缺点：RDB是间隔一段时间进行持久化，如果持久化之间redis发生故障，会发生数据丢失。所以这种方式更适合数据要求不严谨的时候

### rdb触发时机

snapshot触发的时机，是有“间隔时间”和“变更次数”共同决定，同时符合2个条件才会触发snapshot,否则“变更次数”会被继续累加到下一个“间隔时间”上。

### rdb持久化机制

+ rdb数据持久化机制，是对redis中数据执行周期性的持久化，根据当前内存数据生成一份快照文件dump.rdb，这个过程叫snapshotting
+ rdb会生成多分数据文件，每份文件都代表了某一时刻redis中的数据
+ 这种多个数据文件的方式，非常适合做冷备份，可以将完整的数据文件发送到远程的安全存储上，比如阿里云存储，以预定好的备份策略来定期备份redis中的数据
+ 当redis发送故障时，服务器上内存数据和磁盘上的数据都丢失了，可以从云服务上拷贝之前的数据文件，放到指定目录，然后重新启动redis，如果redis只开启了rdb持久化机制，redis重启后,会根据rdb持久化文件重新构建数据

### aof持久化机制

+ aof持久化机制，是以每条写入指令为日志，通过append-only的模式写入一个叫appendonly.aof日志文件中
+ aof持久化机制，可以更好的保护数据不丢失，一般aof会每隔1秒，通过一个后台线程执行一次fsync操作，最多会丢失1秒钟的数据

### rdb持久化机制的工作流程

1. redis使用fork函数创建一个子进程(当前进程（父进程）的副本)
2. 父进程继续处理客服端发送来的命令，子进程开始讲内存中的数据写入到临时文件中
3. 当子进程完成所有数据写入后，会用临时文件替换旧的rdb文件

### 手动触发rdb快照

+ 通过客户端命令save或bgsave，同步或异步执行rdb内存快照。  
+ 两个命令的区别是save是由主进程执行，会阻塞其他请求；而bgsave通过fork的子进程执行
+ redis正常退出也会执行rdb内存快照

### rdb持久化机制的优点

+ rdb持久化生成的数据文件代表了某一时间redis中的数据，这种某一时刻的完整数据非常合适做冷备，可以将这种完整的数据文件发送到远程的安全存储上，如阿里云存储，以预定好的备份策略来备份redis中的数据
+ 当执行rdb持久化时，对redis的性能影响较小，因为redis主进程通过fork一个子进程，让子继承来执行rdb的持久化操作
+ 相较于aof持久化机制来说，直接基于rdb数据文件来恢复redis中的数据，更加快速

### rdb持久化机制的缺点

+ 如果想要在redis发生故障时，尽可能少的丢失数据，rdb没有aof好。一般来说，rdb数据文件是每隔5分钟或者更长时间生成一次，这个时候一旦redis宕机，使用rdb数据文件恢复redis中的数据，会丢失最近5分钟的数据
+ redis中每次fork子进程生成rdb数据文件时，如果数据文件过大，可能会导致暂停对外提供服务数毫秒，甚至数秒
+ 每次快照持久化都是将内存数据完整写入到磁盘一次，并不是增量的只同步脏数据。如果数据量大的话，而且写操作比较多，必然会引起大量的磁盘io操作，可能会严重影响性能。

### aof优缺点

+ 优点：可以保持更高的数据完整性，如果设置追加file的时间是1s，如果redis发生故障，最多会丢失1s的数据；且如果日志写入不完整支持redis-check-aof来进行日志修复；AOF文件没被rewrite之前（文件过大时会对命令进行合并重写），可以删除其中的某些命令（比如误操作的flushall）。
+ 缺点：AOF文件比RDB文件大，且恢复速度慢。

### aof概述

认为AOF就是日志文件，此文件只会记录“变更操作”(例如：set/del等)，如果server中持续的大量变更操作，将会导致AOF文件非常的庞大，意味着server失效后，数据恢复的过程将会很长；事实上，一条数据经过多次变更，将会产生多条AOF记录，其实只要保存当前的状态，历史的操作记录是可以抛弃的；因为AOF持久化模式还伴生了“AOF rewrite”。 
AOF的特性决定了它相对比较安全，如果你期望数据更少的丢失，那么可以采用AOF模式。如果AOF文件正在被写入时突然server失效，有可能导致文件的最后一次记录是不完整，你可以通过手工或者程序的方式去检测并修正不完整的记录，以便通过aof文件恢复能够正常；同时需要提醒，如果你的redis持久化手段中有aof，那么在server故障失效后再次启动前，需要检测aof文件的完整性。

### aof持久化机制的优点

+ aof可以更好的保护数据不丢失，一般aof会每隔1s，通过一个后台线程执行一次fsync操作，最后丢失1秒钟的数据
+ aof日志文件已appond-only模式写入，所以没有任何磁盘寻址开销，写入性能非常高，而且文件不容易破损，即使文件尾部破损，也很容易修复
+ aof日志文件即使过大，也会出现日志文件重新操作，创建一份最小的日志文件
+ aof日志文件的命令通过非常刻度的方式进行记录，这个特性非常合适做灾难性误删除的紧急恢复
+ 比如某人不小心用flushall命令清空了所有数据，只要这个时候后台rewrite还没有发生，那么就可以立即拷贝AOF文件，将最后一条flushall命令给删了，然后再将该AOF文件放回去，就可以通过恢复机制，自动恢复所有数据
+ “日志文件”保存了历史所有的操作过程；当server需要数据恢复时，可以直接replay此日志文件，即可还原所有的操作过程。

### aof的缺点

+ 对于同一份数据来说，AOF日志文件通常比RDB数据快照文件更大
+ AOF开启后，支持的写QPS会比RDB支持的写QPS低，因为AOF一般会配置成每秒fsync一次日志文件

### aof rewrite流程

redis中的数据容量是有限的，很多数据可能会自动过期，可能会被用户删除，可能会被redis用缓存清除的算法清理掉

redis中的数据会不断被淘汰掉，就一部分常用的数据会保留在redis内存中

所以可能很多之前的已被清理掉的数据，对应的写日志还停留在AOF中，AOF日志文件就一个，会不断的膨胀，到很大很大

AOF文件会不断增大，它的大小直接影响“故障恢复”的时间,而且AOF文件中历史操作是可以丢弃的。AOF rewrite操作就是“压缩”AOF文件的过程，当然redis并没有采用“基于原aof文件”来重写的方式，而是采取了类似snapshot的方式：基于copy-on-write，全量遍历内存中数据，然后逐个序列到aof文件中。因此AOF rewrite能够正确反应当前内存数据的状态，这正是我们所需要的；*rewrite过程中，对于新的变更操作将仍然被写入到原AOF文件中，同时这些新的变更操作也会被redis收集起来(buffer，copy-on-write方式下，最极端的可能是所有的key都在此期间被修改，将会耗费2倍内存)，当内存数据被全部写入到新的aof文件之后，收集的新的变更操作也将会一并追加到新的aof文件中，此后将会重命名新的aof文件为appendonly.aof,此后所有的操作都将被写入新的aof文件。如果在rewrite过程中，出现故障，将不会影响原AOF文件的正常工作，只有当rewrite完成之后才会切换文件，因为rewrite过程是比较可靠的

（1）redis fork一个子进程
（2）子进程基于当前内存中的数据，构建日志，开始往一个新的临时的AOF文件中写入日志
（3）redis主进程，接收到client新的写操作之后，在内存中写入日志，同时新的日志也继续写入旧的AOF文件，并将fork子进程后的写指令缓存起来
（4）子进程写完新的日志文件之后，redis主进程将内存中的新日志再次追加到新的AOF文件中
（5）用新的日志文件替换掉旧的日志文件

### AOF破损文件的修复

如果redis在append数据到AOF文件时，机器宕机了，可能会导致AOF文件破损
用redis-check-aof --fix命令来修复破损的AOF文件

### aof - fsync概述

redis每次接收到一条写命令，就会写入日志文件中，当然是先写入os cache的，然后每隔一定时间再fsync一下  
AOF是文件操作，对于变更操作比较密集的server，那么必将造成磁盘IO的负荷加重；此外linux对文件操作采取了“延迟写入”手段，即并非每次write操作都会触发实际磁盘操作，而是进入了buffer中，当buffer数据达到阀值时触发实际写入(也有其他时机)，这是linux对文件系统的优化，但是这却有可能带来隐患，如果buffer没有刷新到磁盘，此时物理机器失效(比如断电)，那么有可能导致最后一条或者多条aof记录的丢失


### 同时开启rdb与aof,优先从哪个数据文件重新构建redis中的数据

在同时开启rdb和aof时，redis重新启动，会优先从aof文件中恢复数据，因为一般来说aof文件中的数据，相对于rdb文件中的数据，更加完整

### rdb与aof如何选择

1. 不要仅仅使用RDB，因为那样会导致你丢失很多数据

2. 也不要仅仅使用AOF，因为那样有两个问题，第一，你通过AOF做冷备，没有RDB做冷备，来的恢复速度更快; 第二，RDB每次简单粗暴生成数据快照，更加健壮，可以避免AOF这种复杂的备份和恢复机制的bug

3. 综合使用AOF和RDB两种持久化机制，用AOF来保证数据不丢失，作为数据恢复的第一选择; 用RDB来做不同程度的冷备，在AOF文件都丢失或损坏不可用的时候，还可以使用RDB来进行快速的数据恢复

### rewrite与snapshot是否可以同时执行

因为rewrite操作/aof记录同步/snapshot都消耗磁盘IO，redis采取了“schedule”策略：无论是“人工干预”还是系统触发，snapshot和rewrite需要逐个被执行。

### AOF和RDB同时工作

（1）如果RDB在执行snapshotting操作，那么redis不会执行AOF rewrite; 如果redis再执行AOF rewrite，那么就不会执行RDB snapshotting
（2）如果RDB在执行snapshotting，此时用户执行BGREWRITEAOF命令，那么等RDB快照生成之后，才会去执行AOF rewrite
（3）同时有RDB snapshot文件和AOF日志文件，那么redis重启的时候，会优先使用AOF进行数据恢复，因为其中的日志更完整

### 手动触发rewrite操作

`BGREWRITEAOF`

### aof和 rdb总结

AOF和RDB各有优缺点，这是有它们各自的特点所决定：

+ AOF更加安全，可以将数据更加及时的同步到文件中，但是AOF需要较多的磁盘IO开支，AOF文件尺寸较大，文件内容恢复数度相对较慢。
+ snapshot，安全性较差，它是“正常时期”数据备份以及master-slave数据同步的最佳手段，文件尺寸较小，恢复数度较快。
+ 可以通过配置文件来指定它们中的一种，或者同时使用它们(不建议同时使用)，或者全部禁用，在架构良好的环境中，master通常使用AOF，slave使用snapshot，主要原因是master需要首先确保数据完整性，它作为数据备份的第一选择；slave提供只读服务(目前slave只能提供读取服务)，它的主要目的就是快速响应客户端read请求；但是如果你的redis运行在网络稳定性差/物理环境糟糕情况下，建议你master和slave均采取AOF，这个在master和slave角色切换时，可以减少“人工数据备份”/“人工引导数据恢复”的时间成本；如果你的环境一切非常良好，且服务需要接收密集性的write操作，那么建议master采取snapshot，而slave采用AOF。

### rdb快照snapshot触发的时机

`save 900 1`  #15分钟来至少有1个键被更改则执行rdb内存快照  
`save 300 10`  #5分钟来至少有10个键被更改则执行rdb内存快照  
`save 60 10000`  #1分钟来至少有10000个键被更改则执行rdb内存快照  
> 以上条件只要满足一条就执行内存快照

### 指定rdb文件名

`dbfilename dump.rdb`

### rdb开启文件压缩

`rdbcompression yes`  默认为“yes”，压缩往往意味着“额外的cpu消耗”，同时也意味这较小的文件尺寸以及较短的网络传输时间

### rdb执行bgsave错误，是否停止接受写请求

`stop-writes-on-bgsave-error yes` 默认是"yes"
### 开启aof持久化机制

`appendonly yes` 此选项为aof功能的开关，默认为“no”，可以通过“yes”来开启aof功能  

### 指定aof文件名

`appendfilename appendonly.aof`

### 指定aof操作fsync策略

`appendfsync everysec`

+ always:每次写入一条数据，立即将这个数据对应的写日志fsync到磁盘上去，确保redis里的数据一条都不丢，但性能非常差
+ everysec：每秒将os cache中的数据fsync到磁盘，性能和安全都比较中庸的方式，也是redis推荐的方式。如果遇到物理服务器故障，有可能导致最近一秒内aof记录丢失(可能为部分丢失)。
+ no：redis负责仅仅将数据写入os cache就不管了，而后写入磁盘的操作由系统来处理。操作系统可以根据buffer填充情况/通道空闲时间等择机触发同步；这是一种普通的文件操作方式。性能较好，在物理服务器故障时，数据丢失量会因OS配置有关。

### 在aof-rewrite期间，appendfsync是否暂缓文件同步

`no-appendfsync-on-rewrite no`
> "no"表示“不暂缓”，“yes”表示“暂缓”，默认为“no”

### 触发aof-rewrite操作

`auto-aof-rewrite-min-size 64mb`  aof文件rewrite触发的最小文件尺寸(mb,gb),只有大于此aof文件大于此尺寸是才会触发rewrite，默认“64mb”，建议“512mb” 

`auto-aof-rewrite-percentage 100`  相对于“上一次”rewrite，本次rewrite触发时aof文件应该增长的百分比。  每一次rewrite之后，redis都会记录下此时“新aof”文件的大小(例如A)，那么当aof文件增长到A*(1 + p)之后。触发下一次rewrite，每一次aof记录的添加，都会检测当前aof文件的尺寸。  

### 指定持久化文件存储目录

`dir /var/redis/6379`

### redis高并发跟整个系统的高并发之间的关系

在没做缓存的架构中，可以通过数据库的主从复制，一系列复杂的分库分表，也是能达到支持高并发，QPS上万的要求，但是每次请求都从磁盘中读取，再经过一系列的业务逻辑处理，响应结果，如果要支持更高的QPS，上10W,100W，那么磁盘IO性能将为称为整个系统的瓶颈，redis是基于内存的数据服务器，将磁盘中数据，或经业务处理后的缓存到内存数据服务器上，下次请求不从磁盘，这样系统能处理能处理更多的请求，支持更高的QPS

### redis不能支撑高并发的瓶颈在哪里？

单机版redis能支持读写请求时有限的，系统要支持更高的QPS，需要redis集群，有更大的内存空间，支持更高的读写请求

### 如果redis要支撑超过10万+的并发，那应该怎么做？

redis单机一般不支持超10W的QPS，除非一些特殊的情况，服务器的配置特别高，性能特别好，
一般redis单机支持的QPS在几万左右，
在写请求少，读请求多的时候，可以做redis的主从架构，读写分离，一主多从可以支持更高的QPS，
当缓存架构需要保存的数据量比较大的时候，可以做redis cluster。

### redis replication基本原理

redis提供主从功能，主redis提供写服务，从redis提供读服务，主redis定时将当前内存快照发送给从redis，进行主从数据同步

### redis replication 核心机制

redis采用异步方式复制数据到从节点，从2.8版本开始，redis会周期性地确认自己复制的数据量
一个master可以配置多个slave节点
slave节点也可以连接其他slave节点
slave做复制的时候不会阻塞master节点的正常工作，也不会阻塞对自己的查询操作，它会用旧的数据提供服务，
但服务完成的时候，需要删除旧的数据集，这个时候会暂停对外服务
slave节点主要用于横向扩容，做读写分离，提高读的吞吐量

### master持久化对主从架构的安全保障的意义

1. 如果master不做持久化，当master宕机重启后，master的数据时空，一经复制slave节点的数据也丢了
2. 如果将slave做为master的数据备份，当master节点挂了，master与slave之间可能数据存在差异，
3. 即使加入了sentinel，在sentinel检测到master挂了之前，master重启，master中的数据丢了，一经复制slave中的数据也丢了

### 主从架构的核心原理

当一个slave节点启动时，会发送psync指令给master节点，
如果slave节点是重新连接，那么master会向slave节点进行增量复制,
如果slave节点是第一次连接，那么master会启动一次全量复制（full resynchronization）

开启全量复制时，master会fork一个子进程，开始生成一份rdb快照文件，同时还会缓存从客户端接受的所有写指令，
rdb文件生成完毕后，master会将这个文件发送给slave,slave会先将数据写入本地磁盘，然后再加载到内存中。
最后master会将缓存的所有写指令发送给slave，slave也会同步这些数据。

如果slave跟master有网络故障，断开连接，会自动重连，
如果master发现有多个slave都来重新建立连接，master仅仅会生成一份数据快照文件，用一份数据同步所有slave

### 主从复制的断点续传

从redis 2.8开始，就支持主从复制的断点续传，如果主从复制过程中，网络连接断掉了，那么可以接着上次复制的地方，继续复制下去，而不是从头开始复制一份

master node会在内存中创建一个backlog，master和slave都会保存一个replica offset还有一个master id，offset就是保存在backlog中的。
如果master和slave网络连接断掉了，slave会让master从上次的replica offset开始继续复制
但是如果没有找到对应的offset，那么就会执行一次resynchronization

### 无磁盘化复制

master在内存中直接创建rdb，然后发送给slave，不会在自己本地落地磁盘了
repl-diskless-sync
repl-diskless-sync-delay，等待一定时长再开始复制，因为要等更多slave重新连接过来

一个RDB文件从master端传到slave端，分为两种情况： 
1、支持disk：master端将RDB file写到disk，稍后再传送到slave端； 
2、无磁盘diskless：master端直接将RDB file传到slave socket，不需要与disk进行交互。 
无磁盘diskless方式适合磁盘读写速度慢但网络带宽非常高的环境。 
repl-diskless-sync no 默认不使用diskless同步方式 
repl-diskless-sync-delay 5 无磁盘diskless方式在进行数据传递之前会有一个时间的延迟，以便slave端能够进行到待传送的目标队列中，这个时间默认是5秒 

### 过期key处理

slave不会过期key，只会等待master过期key。如果master过期了一个key，或者通过LRU淘汰了一个key，那么会模拟一条del命令发送给slave。

## redis replication 详细流程及专业词汇解释

### 复制的完整流程

（1）slave node启动，仅仅保存master node的信息，包括master node的host和ip，但是复制流程没开始
master host和ip是从哪儿来的，redis.conf里面的slaveof配置的
（2）slave node内部有个定时任务，每秒检查是否有新的master node要连接和复制，如果发现，就跟master node建立socket网络连接
（3）slave node发送ping命令给master node
（4）口令认证，如果master设置了requirepass，那么salve node必须发送masterauth的口令过去进行认证
（5）master node第一次执行全量复制，将所有数据发给slave node
（6）master node后续持续将写命令，异步复制给slave node

### 数据同步相关的核心机制

指的就是第一次slave连接master的时候，执行的全量复制，那个过程里面的一些细节的机制
（1）master和slave都会维护一个offset
master会在自身不断累加offset，slave也会在自身不断累加offset
slave每秒都会上报自己的offset给master，同时master也会保存每个slave的offset
这个倒不是说特定就用在全量复制的，主要是master和slave都要知道各自的数据的offset，才能知道互相之间的数据不一致的情况
（2）backlog
master node有一个backlog，默认是1MB大小
master node给slave node复制数据时，也会将数据在backlog中同步写一份
backlog主要是用来做断点续传时的增量复制
（3）master run id
info server，可以看到master run id
如果根据host+ip定位master node，是不靠谱的，如果master node重启或者数据出现了变化，那么slave node应该根据不同的run id区分，run id不同就做全量复制
如果需要不更改run id重启redis，可以使用redis-cli debug reload命令
（4）psync
从节点使用psync从master node进行复制，psync runid offset
master node会根据自身的情况返回响应信息，可能是FULLRESYNC runid offset触发全量复制，可能是CONTINUE触发增量复制

### 全量复制

（1）master执行bgsave，在本地生成一份rdb快照文件
（2）master node将rdb快照文件发送给salve node，如果rdb复制时间超过60秒（repl-timeout），那么slave node就会认为复制失败，可以适当调节大这个参数
（3）对于千兆网卡的机器，一般每秒传输100MB，6G文件，很可能超过60s
（4）master node在生成rdb时，会将所有新的写命令缓存在内存中，在salve node保存了rdb之后，再将新的写命令复制给salve node
（5）client-output-buffer-limit slave 256MB 64MB 60，如果在复制期间，内存缓冲区持续消耗超过64MB，或者一次性超过256MB，那么停止复制，复制失败
（6）slave node接收到rdb之后，清空自己的旧数据，然后重新加载rdb到自己的内存中，同时基于旧的数据版本对外提供服务
（7）如果slave node开启了AOF，那么会立即执行BGREWRITEAOF，重写AOF

rdb生成、rdb通过网络拷贝、slave旧数据的清理、slave aof rewrite，很耗费时间
如果复制的数据量在4G~6G之间，那么很可能全量复制时间消耗到1分半到2分钟

### 增量复制

（1）如果全量复制过程中，master-slave网络连接断掉，那么salve重新连接master时，会触发增量复制
（2）master直接从自己的backlog中获取部分丢失的数据，发送给slave node，默认backlog就是1MB
（3）msater就是根据slave发送的psync中的offset来从backlog中获取数据的

### heartbeat

主从节点互相都会发送heartbeat信息
master默认每隔10秒发送一次heartbeat，salve node每隔1秒发送一个heartbeat

### 异步复制

master每次接收到写命令之后，先在内部写入数据，然后异步发送给slave node

### 单机版redis安装

```txt
1、安装单机版redis

大家可以自己去官网下载，当然也可以用课程提供的压缩包

wget http://downloads.sourceforge.net/tcl/tcl8.6.1-src.tar.gz
tar -xzvf tcl8.6.1-src.tar.gz
cd  /usr/local/tcl8.6.1/unix/
./configure  
make && make install

使用redis-3.2.8.tar.gz（截止2017年4月的最新稳定版）
tar -zxvf redis-3.2.8.tar.gz
cd redis-3.2.8
make && make test && make install

------------------------------------------------------------------------

2、redis的生产环境启动方案

如果一般的学习课程，你就随便用redis-server启动一下redis，做一些实验，这样的话，没什么意义

要把redis作为一个系统的daemon进程去运行的，每次系统启动，redis进程一起启动

（1）redis utils目录下，有个redis_init_script脚本
（2）将redis_init_script脚本拷贝到linux的/etc/init.d目录中，将redis_init_script重命名为redis_6379，6379是我们希望这个redis实例监听的端口号
（3）修改redis_6379脚本的第6行的REDISPORT，设置为相同的端口号（默认就是6379）
（4）创建两个目录：/etc/redis（存放redis的配置文件），/var/redis/6379（存放redis的持久化文件）
（5）修改redis配置文件（默认在根目录下，redis.conf），拷贝到/etc/redis目录中，修改名称为6379.conf

（6）修改redis.conf中的部分配置为生产环境

daemonize	yes							让redis以daemon进程运行
pidfile		/var/run/redis_6379.pid 	设置redis的pid文件位置
port		6379						设置redis的监听端口号
dir 		/var/redis/6379				设置持久化文件的存储位置
#logfile	/var/redis/6379.log			指定日志文件名

（7）启动redis，执行cd /etc/init.d, chmod 777 redis_6379，./redis_6379 start

（8）确认redis进程是否启动，ps -ef | grep redis

（9）让redis跟随系统启动自动启动

在redis_6379脚本中，最上面，加入两行注释

# chkconfig:   2345 90 10

# description:  Redis is a persistent key-value database

chkconfig redis_6379 on

------------------------------------------------------------------------

3、redis cli的使用

redis-cli SHUTDOWN，连接本机的6379端口停止redis进程

redis-cli -h 127.0.0.1 -p 6379 SHUTDOWN，制定要连接的ip和端口号

redis-cli PING，ping redis的端口，看是否正常

redis-cli，进入交互式命令行

SET k1 v1
GET k1
```

### 部署redis主从

```txt
从配置config文件中
slaveof <masterip> <masterport>
如 slaveof 192.168.1.1 6379
或使用命令
redis-server --slaveof redis-master 6379
```

### 强制读写分离

基于主从复制架构，实现读写分离

redis slave node只读，默认开启，slave-read-only

开启了只读的redis slave node，会拒绝所有的写操作，这样可以强制搭建成读写分离的架构

### 集群安全认证

master上启用安全认证,设置连接密码，requirepass
从节点配置master连接口令，masterauth

### 读写分离架构的测试

```txt
先启动主节点，eshop-cache01上的redis实例
再启动从节点，eshop-cache02上的redis实例

刚才我调试了一下，redis slave node一直说没法连接到主节点的6379的端口

在搭建生产环境的集群的时候，不要忘记修改一个配置，bind

bind 127.0.0.1 -> 本地的开发调试的模式，就只能127.0.0.1本地才能访问到6379的端口
若要局网内能连接到redis,配置 redis bind:
bind 127.0.0.1 本机ip

每个redis.conf中的bind 127.0.0.1 -> bind自己的ip地址
在每个节点上都: iptables -A INPUT -ptcp --dport  6379 -j ACCEPT

redis-cli -h ipaddr -p port
info replication

docker命令
docker exec -ti redis-master redis-cli -h redis-slave -p 6379
docker exec -ti redis-master redis-cli -h redis-master -p 6379

```

### 对redis读写分离架构进行压测，单实例写QPS+单实例读QPS

```txt
redis自己提供的redis-benchmark压测工具，是最快捷最方便的
redis-benchmark -h 192.168.31.187

-c <clients>       Number of parallel connections (default 50)
-n <requests>      Total number of requests (default 100000)
-d <size>          Data size of SET/GET value in bytes (default 2)

QPS的两个杀手：一个是复杂操作，lrange，挺多的;
```
### 企业级数据备份方案

```
RDB非常适合做冷备，每次生成之后，就不会再有修改了

数据备份方案

（1）写crontab定时调度脚本去做数据备份
（2）每小时都copy一份rdb的备份，到一个目录中去，仅仅保留最近48小时的备份
（3）每天都保留一份当日的rdb的备份，到一个目录中去，仅仅保留最近1个月的备份
（4）每次copy备份的时候，都把太旧的备份给删了
（5）每天晚上将当前服务器上所有的数据备份，发送一份到远程的云服务上去

/usr/local/redis

每小时copy一次备份，删除48小时前的数据

crontab -e

0 * * * * sh /usr/local/redis/copy/redis_rdb_copy_hourly.sh

redis_rdb_copy_hourly.sh

#!/bin/sh 

cur_date=`date +%Y%m%d%k`
rm -rf /usr/local/redis/snapshotting/$cur_date
mkdir /usr/local/redis/snapshotting/$cur_date
cp /var/redis/6379/dump.rdb /usr/local/redis/snapshotting/$cur_date

del_date=`date -d -48hour +%Y%m%d%k`
rm -rf /usr/local/redis/snapshotting/$del_date

每天copy一次备份

crontab -e

0 0 * * * sh /usr/local/redis/copy/redis_rdb_copy_daily.sh

redis_rdb_copy_daily.sh

#!/bin/sh 

cur_date=`date +%Y%m%d`
rm -rf /usr/local/redis/snapshotting/$cur_date
mkdir /usr/local/redis/snapshotting/$cur_date
cp /var/redis/6379/dump.rdb /usr/local/redis/snapshotting/$cur_date

del_date=`date -d -1month +%Y%m%d`
rm -rf /usr/local/redis/snapshotting/$del_date

每天一次将所有数据上传一次到远程的云服务器上去
```

### 数据恢复方案

```
（1）如果是redis进程挂掉，那么重启redis进程即可，直接基于AOF日志文件恢复数据

不演示了，在AOF数据恢复那一块，演示了，fsync everysec，最多就丢一秒的数

（2）如果是redis进程所在机器挂掉，那么重启机器后，尝试重启redis进程，尝试直接基于AOF日志文件进行数据恢复

AOF没有破损，也是可以直接基于AOF恢复的

AOF append-only，顺序写入，如果AOF文件破损，那么用redis-check-aof fix

（3）如果redis当前最新的AOF和RDB文件出现了丢失/损坏，那么可以尝试基于该机器上当前的某个最新的RDB数据副本进行数据恢复

当前最新的AOF和RDB文件都出现了丢失/损坏到无法恢复，一般不是机器的故障，人为

大数据系统，hadoop，有人不小心就把hadoop中存储的大量的数据文件对应的目录，rm -rf一下，我朋友的一个小公司，运维不太靠谱，权限也弄的不太好

/var/redis/6379下的文件给删除了

找到RDB最新的一份备份，小时级的备份可以了，小时级的肯定是最新的，copy到redis里面去，就可以恢复到某一个小时的数据

appendonly.aof + dump.rdb，优先用appendonly.aof去恢复数据，但是我们发现redis自动生成的appendonly.aof是没有数据的

然后我们自己的dump.rdb是有数据的，但是明显没用我们的数据

redis启动的时候，自动重新基于内存的数据，生成了一份最新的rdb快照，直接用空的数据，覆盖掉了我们有数据的，拷贝过去的那份dump.rdb

你停止redis之后，其实应该先删除appendonly.aof，然后将我们的dump.rdb拷贝过去，然后再重启redis

很简单，就是虽然你删除了appendonly.aof，但是因为打开了aof持久化，redis就一定会优先基于aof去恢复，即使文件不在，那就创建一个新的空的aof文件

停止redis，暂时在配置中关闭aof，然后拷贝一份rdb过来，再重启redis，数据能不能恢复过来，可以恢复过来

脑子一热，再关掉redis，手动修改配置文件，打开aof，再重启redis，数据又没了，空的aof文件，所有数据又没了

在数据安全丢失的情况下，基于rdb冷备，如何完美的恢复数据，同时还保持aof和rdb的双开

停止redis，关闭aof，拷贝rdb备份，重启redis，确认数据恢复，直接在命令行热修改redis配置，打开aof，这个redis就会将内存中的数据对应的日志，写入aof文件中

此时aof和rdb两份数据文件的数据就同步了

redis config set热修改配置参数，可能配置文件中的实际的参数没有被持久化的修改，再次停止redis，手动修改配置文件，打开aof的命令，再次重启redis

（4）如果当前机器上的所有RDB文件全部损坏，那么从远程的云服务上拉取最新的RDB快照回来恢复数据

（5）如果是发现有重大的数据错误，比如某个小时上线的程序一下子将数据全部污染了，数据全错了，那么可以选择某个更早的时间点，对数据进行恢复

举个例子，12点上线了代码，发现代码有bug，导致代码生成的所有的缓存数据，写入redis，全部错了

找到一份11点的rdb的冷备，然后按照上面的步骤，去恢复到11点的数据，不就可以了吗

```

### 哨兵的介绍

sentinal，中文名是哨兵

哨兵是redis集群架构中非常重要的一个组件，主要功能如下

（1）集群监控，负责监控redis master和slave进程是否正常工作
（2）消息通知，如果某个redis实例有故障，那么哨兵负责发送消息作为报警通知给管理员
（3）故障转移，如果master node挂掉了，会自动转移到slave node上
（4）配置中心，如果故障转移发生了，通知client客户端新的master地址

哨兵本身也是分布式的，作为一个哨兵集群去运行，互相协同工作

（1）故障转移时，判断一个master node是宕机了，需要大部分的哨兵都同意才行，涉及到了分布式选举的问题
（2）即使部分哨兵节点挂掉了，哨兵集群还是能正常工作的，因为如果一个作为高可用机制重要组成部分的故障转移系统本身是单点的，那就很坑爹了

目前采用的是sentinal 2版本，sentinal 2相对于sentinal 1来说，重写了很多代码，主要是让故障转移的机制和算法变得更加健壮和简单

### 哨兵的核心知识

（1）哨兵至少需要3个实例，来保证自己的健壮性
（2）哨兵 + redis主从的部署架构，是不会保证数据零丢失的，只能保证redis集群的高可用性
（3）对于哨兵 + redis主从这种复杂的部署架构，尽量在测试环境和生产环境，都进行充足的测试和演练

### 为什么redis哨兵集群只有2个节点无法正常工作？

哨兵集群必须部署2个以上节点

如果哨兵集群仅仅部署了个2个哨兵实例，quorum=1

+----+         +----+
| M1 |---------| R1 |
| S1 |         | S2 |
+----+         +----+

Configuration: quorum = 1

master宕机，s1和s2中只要有1个哨兵认为master宕机就可以还行切换，同时s1和s2中会选举出一个哨兵来执行故障转移

同时这个时候，需要majority，也就是大多数哨兵都是运行的，2个哨兵的majority就是2（2的majority=2，3的majority=2，5的majority=3，4的majority=2），
2个哨兵都运行着，就可以允许执行故障转移

但是如果整个M1和S1运行的机器宕机了，那么哨兵只有1个了，此时就没有majority来允许执行故障转移，虽然另外一台机器还有一个R1，但是故障转移不会执行

### 经典的3节点哨兵集群

       +----+
       | M1 |
       | S1 |
       +----+
          |
+----+    |    +----+
| R2 |----+----| R3 |
| S2 |         | S3 |
+----+         +----+

0   | M1  | 0
--- | --- | ---
0   | S1  |  
R2  |     | R3
S2  |     | S3

Configuration: quorum = 2，majority

如果M1所在机器宕机了，那么三个哨兵还剩下2个，S2和S3可以一致认为master宕机，然后选举出一个来执行故障转移

同时3个哨兵的majority是2，所以还剩下的2个哨兵运行着，就可以允许执行故障转移

### redis中两种数据丢失的情况

主备切换的过程，可能会导致数据丢失

（1）异步复制导致的数据丢失

因为master -> slave的复制是异步的，所以可能有部分数据还没复制到slave，master就宕机了，此时这些部分数据就丢失了

（2）脑裂导致的数据丢失

脑裂，也就是说，某个master所在机器突然脱离了正常的网络，跟其他slave机器不能连接，但是实际上master还运行着

此时哨兵可能就会认为master宕机了，然后开启选举，将其他slave切换成了master

这个时候，集群里就会有两个master，也就是所谓的脑裂

此时虽然某个slave被切换成了master，但是可能client还没来得及切换到新的master，还继续写向旧master的数据，这些数据可能就丢失了

因为旧master再次恢复的时候，会被作为一个slave挂到新的master上去，自己的数据会清空，重新从新的master复制数据

### 解决异步复制和脑裂导致的数据丢失

min-slaves-to-write 1
min-slaves-max-lag 10

要求至少有1个slave，数据复制和同步的延迟不能超过10秒

如果说一旦所有的slave，数据复制和同步的延迟都超过了10秒钟，那么这个时候，master就不会再接收任何请求了

上面两个配置可以减少异步复制和脑裂导致的数据丢失

（1）减少异步复制的数据丢失

有了min-slaves-max-lag这个配置，就可以确保说，一旦slave复制数据和ack延时太长，就认为可能master宕机后损失的数据太多了，那么就拒绝写请求，
这样可以把master宕机时由于部分数据未同步到slave导致的数据丢失降低的可控范围内

（2）减少脑裂的数据丢失

如果一个master出现了脑裂，跟其他slave丢了连接，那么上面两个配置可以确保说，如果不能继续给指定数量的slave发送数据，而且slave超过10秒没有给自己ack消息，
那么就直接拒绝客户端的写请求

这样脑裂后的旧master就不会接受client的新数据，也就避免了数据丢失

上面的配置就确保了，如果跟任何一个slave丢了连接，在10秒后发现没有slave给自己ack，那么就拒绝新的写请求

因此在脑裂场景下，最多就丢失10秒的数据

### sdown和odown转换机制

sdown和odown两种失败状态

sdown是主观宕机，就一个哨兵如果自己觉得一个master宕机了，那么就是主观宕机

odown是客观宕机，如果quorum数量的哨兵都觉得一个master宕机了，那么就是客观宕机

sdown达成的条件很简单，如果一个哨兵ping一个master，超过了down-after-milliseconds指定的毫秒数之后，就主观认为master宕机

sdown到odown转换的条件很简单，如果一个哨兵在指定时间内，收到了quorum指定数量的其他哨兵也认为那个master是sdown了，那么就认为是odown了，客观认为master宕机

### 哨兵集群的自动发现机制

哨兵互相之间的发现，是通过redis的pub/sub系统实现的，每个哨兵都会往__sentinel__:hello这个channel里发送一个消息，
这时候所有其他哨兵都可以消费到这个消息，并感知到其他的哨兵的存在

每隔两秒钟，每个哨兵都会往自己监控的某个master+slaves对应的__sentinel__:hello channel里发送一个消息，内容是自己的host、ip和runid还有对这个master的监控配置

每个哨兵也会去监听自己监控的每个master+slaves对应的__sentinel__:hello channel，然后去感知到同样在监听这个master+slaves的其他哨兵的存在

每个哨兵还会跟其他哨兵交换对master的监控配置，互相进行监控配置的同步

### slave配置的自动纠正

哨兵会负责自动纠正slave的一些配置，比如slave如果要成为潜在的master候选人，哨兵会确保slave在复制现有master的数据;
 如果slave连接到了一个错误的master上，比如故障转移之后，那么哨兵会确保它们连接到正确的master上

### slave->master选举算法

如果一个master被认为odown了，而且majority哨兵都允许了主备切换，那么某个哨兵就会执行主备切换操作，此时首先要选举一个slave

选举slave时，会考虑一些信息

（1）跟master断开连接的时长
（2）slave优先级
（3）复制offset
（4）run id

如果一个slave跟master断开连接已经超过了down-after-milliseconds的10倍，外加master宕机的时长，那么slave就被认为不适合选举为master

(down-after-milliseconds * 10) + milliseconds_since_master_is_in_SDOWN_state

接下来会对slave进行排序

（1）按照slave优先级进行排序，slave priority越低，优先级就越高
（2）如果slave priority相同，那么看replica offset，哪个slave复制了越多的数据，offset越靠后，优先级就越高
（3）如果上面两个条件都相同，那么选择一个run id比较小的那个slave

### quorum和majority

每次一个哨兵要做主备切换，首先需要quorum数量的哨兵认为odown，然后选举出一个哨兵来做切换，这个哨兵还得得到majority哨兵的授权，才能正式执行切换

如果quorum < majority，比如5个哨兵，majority就是3，quorum设置为2，那么就3个哨兵授权就可以执行切换

但是如果quorum >= majority，那么必须quorum数量的哨兵都授权，比如5个哨兵，quorum是5，那么必须5个哨兵都同意授权，才能执行切换

### configuration epoch

哨兵会对一套redis master+slave进行监控，有相应的监控的配置

执行切换的那个哨兵，会从要切换到的新master（salve->master）那里得到一个configuration epoch，这就是一个version号，每次切换的version号都必须是唯一的

如果第一个选举出的哨兵切换失败了，那么其他哨兵，会等待failover-timeout时间，然后接替继续执行切换，此时会重新获取一个新的configuration epoch，作为新的version号

### configuraiton传播

哨兵完成切换之后，会在自己本地更新生成最新的master配置，然后同步给其他的哨兵，就是通过之前说的pub/sub消息机制

这里之前的version号就很重要了，因为各种消息都是通过一个channel去发布和监听的，所以一个哨兵完成一次新的切换之后，新的master配置是跟着新的version号的

其他的哨兵都是根据版本号的大小来更新自己的master配置的

### 哨兵的配置文件

```
sentinel.conf

最小的配置

每一个哨兵都可以去监控多个maser-slaves的主从架构

因为可能你的公司里，为不同的项目，部署了多个master-slaves的redis主从集群

相同的一套哨兵集群，就可以去监控不同的多个redis主从集群

你自己给每个redis主从集群分配一个逻辑的名称

sentinel monitor mymaster 127.0.0.1 6379 2
sentinel down-after-milliseconds mymaster 60000
sentinel failover-timeout mymaster 180000
sentinel parallel-syncs mymaster 1

sentinel monitor resque 192.168.1.3 6380 4
sentinel down-after-milliseconds resque 10000
sentinel failover-timeout resque 180000
sentinel parallel-syncs resque 5

sentinel monitor mymaster 127.0.0.1 6379 

类似这种配置，来指定对一个master的监控，给监控的master指定的一个名称，因为后面分布式集群架构里会讲解，可以配置多个master做数据拆分

port <sentinel-port> :::sentinal监听的端口
dir <working-directory> :::工作目录
bind 127.0.0.1 :::设置能连接到sentinel的主机ip,设置127.0.0.1只能通过本机连接,如果想要局域网内连接,可以设置本机ip地址 bind 127.0.0.1 本机ip
sentinel monitor <master-name> <ip> <redis-port> <quorum> :::设置要监控的主redis的别名，主机地址，端口，quorum
sentinel auth-pass <master-name> <password> :::设置连接redis认证密码
sentinel down-after-milliseconds <master-name> <milliseconds> :::设置判断redis宕机的超时时间
sentinel failover-timeout <master-name> <milliseconds> :::设置主从切换失败后的超时时间
sentinel parallel-syncs <master-name> <numslaves> :::设置主从切换成功后，连接主redis可以同步进行数据复制的slave数量


sentinel down-after-milliseconds mymaster 60000
sentinel failover-timeout mymaster 180000
sentinel parallel-syncs mymaster 1

上面的三个配置，都是针对某个监控的master配置的，给其指定上面分配的名称即可

上面这段配置，就监控了两个master node

这是最小的哨兵配置，如果发生了master-slave故障转移，或者新的哨兵进程加入哨兵集群，那么哨兵会自动更新自己的配置文件

sentinel monitor master-group-name hostname port quorum

quorum的解释如下：

（1）至少多少个哨兵要一致同意，master进程挂掉了，或者slave进程挂掉了，或者要启动一个故障转移操作
（2）quorum是用来识别故障的，真正执行故障转移的时候，还是要在哨兵集群执行选举，选举一个哨兵进程出来执行故障转移操作
（3）假设有5个哨兵，quorum设置了2，那么如果5个哨兵中的2个都认为master挂掉了; 2个哨兵中的一个就会做一个选举，选举一个哨兵出来，执行故障转移; 如果5个哨兵中有3个哨兵都是运行的，那么故障转移就会被允许执行

down-after-milliseconds，超过多少毫秒跟一个redis实例断了连接，哨兵就可能认为这个redis实例挂了

parallel-syncs，新的master主备切换之后，同时有多少个slave被切换到去连接新master，重新做同步，数字越低，花费的时间越多

假设你的redis是1个master，4个slave

然后master宕机了，4个slave中有1个切换成了master，剩下3个slave就要挂到新的master上面去

这个时候，如果parallel-syncs是1，那么3个slave，一个一个地连接到新的master上面去，1个挂接完，而且从新的master sync完数据之后，再连接下一个

如果parallel-syncs是3，那么一次性就会把所有slave挂接到新的master上去

failover-timeout，执行故障转移的timeout超时时长
```

### 启动哨兵进程

在eshop-cache01、eshop-cache02、eshop-cache03三台机器上，分别启动三个哨兵进程，组成一个集群，观察一下日志的输出

redis-sentinel /etc/sentinal/5000.conf
redis-server /etc/sentinal/5000.conf --sentinel

两个命令都可以启动sentinel

日志里会显示出来，每个哨兵都能去监控到对应的redis master，并能够自动发现对应的slave

哨兵之间，互相会自动进行发现，用的就是之前说的pub/sub，消息发布和订阅channel消息系统和机制

### 检查哨兵状态

redis-cli -h 192.168.31.187 -p 5000

sentinel master mymaster
SENTINEL slaves mymaster
SENTINEL sentinels mymaster

SENTINEL get-master-addr-by-name mymaster

### 哨兵节点的增加和删除

增加sentinal，会自动发现

删除sentinal的步骤

（1）停止sentinal进程
（2）SENTINEL RESET *，在所有sentinal上执行，清理所有的master状态
（3）SENTINEL MASTER mastername，在所有sentinal上执行，查看所有sentinal对数量是否达成了一致

### slave的永久下线

让master摘除某个已经下线的slave：SENTINEL RESET mastername，在所有的哨兵上面执行

### slave切换为Master的优先级

slave->master选举优先级：slave-priority，值越小优先级越高

### 基于哨兵集群架构下的安全认证

每个slave都有可能切换成master，所以每个实例都要配置两个指令

master上启用安全认证，requirepass
master连接口令，masterauth

sentinal，sentinel auth-pass <master-group-name> <pass>

### 哨兵的生产环境部署

daemonize yes
logfile /var/log/sentinal/5000

### 容灾演练
```
通过哨兵看一下当前的master：SENTINEL get-master-addr-by-name mymaster

把master节点kill -9掉，pid文件也删除掉

查看sentinal的日志，是否出现+sdown字样，识别出了master的宕机问题; 然后出现+odown字样，就是指定的quorum哨兵数量，都认为master宕机了

（1）三个哨兵进程都认为master是sdown了
（2）超过quorum指定的哨兵进程都认为sdown之后，就变为odown
（3）哨兵1是被选举为要执行后续的主备切换的那个哨兵
（4）哨兵1去新的master（slave）获取了一个新的config version
（5）尝试执行failover
（6）投票选举出一个slave区切换成master，每隔哨兵都会执行一次投票
（7）让salve，slaveof noone，不让它去做任何节点的slave了; 把slave提拔成master; 旧的master认为不再是master了
（8）哨兵就自动认为之前的187:6379变成了slave了，19:6379变成了master了
（9）哨兵去探查了一下187:6379这个salve的状态，认为它sdown了

所有哨兵选举出了一个，来执行主备切换操作

如果哨兵的majority都存活着，那么就会执行主备切换操作

再通过哨兵看一下master：SENTINEL get-master-addr-by-name mymaster

尝试连接一下新的master

故障恢复，再将旧的master重新启动，查看是否被哨兵自动切换成slave节点

（1）手动杀掉master
（2）哨兵能否执行主备切换，将slave切换为master
（3）哨兵完成主备切换后，新的master能否使用
（4）故障恢复，将旧的master重新启动
（5）哨兵能否自动将旧的master变为slave，挂接到新的master上面去，而且也是可以使用的
```

### redis的集群架构

redis cluster

支撑N个redis master node，每个master node都可以挂载多个slave node

主从复制的架构，对于每个master来说，写就写到master，然后读就从mater去读

高可用，因为每个master都有salve节点，那么如果mater挂掉，redis cluster这套机制，就会自动将某个slave切换成master

redis cluster（多master + 读写分离 + 高可用）

我们只要基于redis cluster去搭建redis集群即可，不需要手工去搭建replication复制+主从架构+读写分离+哨兵集群+高可用

4、redis cluster vs. replication + sentinal

如果你的数据量很少，主要是承载高并发高性能的场景，比如你的缓存一般就几个G，单机足够了

replication，一个mater，多个slave，要几个slave跟你的要求的读吞吐量有关系，然后自己搭建一个sentinal集群，去保证redis主从架构的高可用性，就可以了

redis cluster，主要是针对海量数据+高并发+高可用的场景，海量数据，如果你的数据量很大，那么建议就用redis cluster

### redis cluster介绍

redis cluster

（1）自动将数据进行分片，每个master上放一部分数据
（2）提供内置的高可用支持，部分master不可用时，还是可以继续工作的

在redis cluster架构下，每个redis要放开两个端口号，比如一个是6379，另外一个就是加10000的端口号，比如16379

16379端口号是用来进行节点间通信的，也就是cluster bus的东西，集群总线。cluster bus的通信，用来进行故障检测，配置更新，故障转移授权

cluster bus用了另外一种二进制的协议，主要用于节点间进行高效的数据交换，占用更少的网络带宽和处理时间

### 最老土的hash算法和弊端（大量缓存重建）

### 一致性hash算法（自动缓存迁移）+虚拟节点（自动负载均衡）

### redis cluster的hash slot算法

redis cluster有固定的16384个hash slot，对每个key计算CRC16值，然后对16384取模，可以获取key对应的hash slot

redis cluster中每个master都会持有部分slot，比如有3个master，那么可能每个master持有5000多个hash slot

hash slot让node的增加和移除很简单，增加一个master，就将其他master的hash slot移动部分过去，减少一个master，就将它的hash slot移动到其他master上去

移动hash slot的成本是非常低的

客户端的api，可以对指定的数据，让他们走同一个hash slot，通过hash tag来实现


### redis cluster的重要配置

cluster-enabled <yes/no>

cluster-config-file <filename>：这是指定一个文件，供cluster模式下的redis实例将集群状态保存在那里，包括集群中其他机器的信息，比如节点的上线和下限，故障转移，不是我们去维护的，给它指定一个文件，让redis自己去维护的

cluster-node-timeout <milliseconds>：节点存活超时时长，超过一定时长，认为节点宕机，master宕机的话就会触发主备切换，slave宕机就不会提供服务

### 在三台机器上启动6个redis实例
```
（1）在eshop-cache03上部署目录

/etc/redis（存放redis的配置文件），/var/redis/6379（存放redis的持久化文件）

（2）编写配置文件

redis cluster集群，要求至少3个master，去组成一个高可用，健壮的分布式的集群，每个master都建议至少给一个slave，3个master，3个slave，最少的要求

正式环境下，建议都是说在6台机器上去搭建，至少3台机器

保证，每个master都跟自己的slave不在同一台机器上，如果是6台自然更好，一个master+一个slave就死了

3台机器去搭建6个redis实例的redis cluster

mkdir -p /etc/redis-cluster
mkdir -p /var/log/redis
mkdir -p /var/redis/7001

port 7001
cluster-enabled yes
cluster-config-file /etc/redis-cluster/node-7001.conf
cluster-node-timeout 15000
daemonize	yes							
pidfile		/var/run/redis_7001.pid 						
dir 		/var/redis/7001		
logfile /var/log/redis/7001.log
bind 192.168.31.187		
appendonly yes

至少要用3个master节点启动，每个master加一个slave节点，先选择6个节点，启动6个实例

将上面的配置文件，在/etc/redis下放6个，分别为: 7001.conf，7002.conf，7003.conf，7004.conf，7005.conf，7006.conf

（3）准备生产环境的启动脚本

在/etc/init.d下，放6个启动脚本，分别为: redis_7001, redis_7002, redis_7003, redis_7004, redis_7005, redis_7006

每个启动脚本内，都修改对应的端口号

（4）分别在3台机器上，启动6个redis实例

将每个配置文件中的slaveof给删除
```

### 创建集群

因为，以前比如公司里面搭建集群，公司里的机器的环境，运维会帮你做好很多事情

在讲课的话，我们手工用从零开始装的linux虚拟机去搭建，那肯定会碰到各种各样的问题

yum install -y ruby
yum install -y rubygems
gem install redis

cp /usr/local/redis-3.2.8/src/redis-trib.rb /usr/local/bin
//先启动redis实例
//在建立集群
redis-trib.rb create --replicas 1 192.168.31.187:7001 192.168.31.187:7002 192.168.31.19:7003 192.168.31.19:7004 192.168.31.227:7005 192.168.31.227:7006

--replicas: 每个master有几个slave

6台机器，3个master，3个slave，尽量自己让master和slave不在一台机器上

yes

redis-trib.rb check 192.168.31.187:7001

### 主从复制+高可用+多master

主从复制：每个master都有一个slave
高可用：master宕机，slave自动被切换过去
多master：横向扩容支持更大数据量


### 实验多master写入 -> 海量数据的分布式存储

你在redis cluster写入数据的时候，其实是你可以将请求发送到任意一个master上去执行

但是，每个master都会计算这个key对应的CRC16值，然后对16384个hashslot取模，找到key对应的hashslot，找到hashslot对应的master

如果对应的master就在自己本地的话，set mykey1 v1，mykey1这个key对应的hashslot就在自己本地，那么自己就处理掉了

但是如果计算出来的hashslot在其他master上，那么就会给客户端返回一个moved error，告诉你，你得到哪个master上去执行这条写入的命令

什么叫做多master的写入，就是每条数据只能存在于一个master上，不同的master负责存储不同的数据，分布式的数据存储

100w条数据，5个master，每个master就负责存储20w条数据，分布式数据存储

### 实验不同master各自的slave读取 -> 读写分离

在这个redis cluster中，如果你要在slave读取数据，那么需要带上readonly指令，get mykey1

redis-cli -c启动，就会自动进行各种底层的重定向的操作

实验redis cluster的读写分离的时候，会发现有一定的限制性，默认情况下，redis cluster的核心的理念，主要是用slave做高可用的，每个master挂一两个slave，
主要是做数据的热备，还有master故障时的主备切换，实现高可用的

redis cluster默认是不支持slave节点读或者写的，跟我们手动基于replication搭建的主从架构不一样的

slave node，readonly，get，这个时候才能在slave node进行读取

redis cluster，主从架构是出来，读写分离，复杂了点，也可以做，jedis客户端，对redis cluster的读写分离支持不太好的

默认的话就是读和写都到master上去执行的

如果你要让最流行的jedis做redis cluster的读写分离的访问，那可能还得自己修改一点jedis的源码，成本比较高

要不然你就是自己基于jedis，封装一下，自己做一个redis cluster的读写分离的访问api

核心的思路，就是说，redis cluster的时候，就没有所谓的读写分离的概念了

读写分离，是为了什么，主要是因为要建立一主多从的架构，才能横向任意扩展slave node去支撑更大的读吞吐量

redis cluster的架构下，实际上本身master就是可以任意扩展的，你如果要支撑更大的读吞吐量，或者写吞吐量，或者数据量，都可以直接对master进行横向扩展就可以了

也可以实现支撑更高的读吞吐的效果

### 加入新master

mkdir -p /var/redis/7007

port 7007
cluster-enabled yes
cluster-config-file /etc/redis-cluster/node-7007.conf
cluster-node-timeout 15000
daemonize	yes							
pidfile		/var/run/redis_7007.pid 						
dir 		/var/redis/7007		
logfile /var/log/redis/7007.log
bind 192.168.31.227		
appendonly yes

搞一个7007.conf，再搞一个redis_7007启动脚本

手动启动一个新的redis实例，在7007端口上

redis-trib.rb add-node 192.168.31.227:7007 192.168.31.187:7001

redis-trib.rb check 192.168.31.187:7001

连接到新的redis实例上，cluster nodes，确认自己是否加入了集群，作为了一个新的master

### reshard一些数据过去

resharding的意思就是把一部分hash slot从一些node上迁移到另外一些node上

redis-trib.rb reshard 192.168.31.187:7001

要把之前3个master上，总共4096个hashslot迁移到新的第四个master上去

How many slots do you want to move (from 1 to 16384)?

1000

### 添加node作为slave

eshop-cache03

mkdir -p /var/redis/7008

port 7008
cluster-enabled yes
cluster-config-file /etc/redis-cluster/node-7008.conf
cluster-node-timeout 15000
daemonize	yes							
pidfile		/var/run/redis_7008.pid 						
dir 		/var/redis/7008		
logfile /var/log/redis/7008.log
bind 192.168.31.227		
appendonly yes

redis-trib.rb add-node --slave --master-id 28927912ea0d59f6b790a50cf606602a5ee48108 192.168.31.227:7008 192.168.31.187:7001

### 删除node

先用resharding将数据都移除到其他节点，确保node为空之后，才能执行remove操作

redis-trib.rb del-node 192.168.31.187:7001 bd5a40a6ddccbd46a0f4a2208eb25d2453c2a8db

2个是1365，1个是1366

当你清空了一个master的hashslot时，redis cluster就会自动将其slave挂载到其他master上去

这个时候就只要删除掉master就可以了


### slave的自动迁移

比如现在有10个master，每个有1个slave，然后新增了3个slave作为冗余，有的master就有2个slave了，有的master出现了salve冗余

如果某个master的slave挂了，那么redis cluster会自动迁移一个冗余的slave给那个master

只要多加一些冗余的slave就可以了

为了避免的场景，就是说，如果你每个master只有一个slave，万一说一个slave死了，然后很快，master也死了，那可用性还是降低了

但是如果你给整个集群挂载了一些冗余slave，那么某个master的slave死了，冗余的slave会被自动迁移过去，作为master的新slave，此时即使那个master也死了

还是有一个slave会切换成master的

之前有一个master是有冗余slave的，直接让其他master其中的一个slave死掉，然后看有冗余slave会不会自动挂载到那个master

### 节点间的内部通信机制

1、基础通信原理

（1）redis cluster节点间采取gossip协议进行通信

跟集中式不同，不是将集群元数据（节点信息，故障，等等）集中存储在某个节点上，而是互相之间不断通信，保持整个集群所有节点的数据是完整的

维护集群的元数据用得，集中式，一种叫做gossip

集中式：好处在于，元数据的更新和读取，时效性非常好，一旦元数据出现了变更，立即就更新到集中式的存储中，其他节点读取的时候立即就可以感知到; 不好在于，所有的元数据的更新压力全部集中在一个地方，可能会导致元数据的存储有压力

gossip：好处在于，元数据的更新比较分散，不是集中在一个地方，更新请求会陆陆续续，打到所有节点上去更新，有一定的延时，降低了压力; 缺点，元数据更新有延时，可能导致集群的一些操作会有一些滞后

我们刚才做reshard，去做另外一个操作，会发现说，configuration error，达成一致

（2）10000端口

每个节点都有一个专门用于节点间通信的端口，就是自己提供服务的端口号+10000，比如7001，那么用于节点间通信的就是17001端口

每隔节点每隔一段时间都会往另外几个节点发送ping消息，同时其他几点接收到ping之后返回pong

（3）交换的信息

故障信息，节点的增加和移除，hash slot信息，等等

2、gossip协议

gossip协议包含多种消息，包括ping，pong，meet，fail，等等

meet: 某个节点发送meet给新加入的节点，让新节点加入集群中，然后新节点就会开始与其他节点进行通信

redis-trib.rb add-node

其实内部就是发送了一个gossip meet消息，给新加入的节点，通知那个节点去加入我们的集群

ping: 每个节点都会频繁给其他节点发送ping，其中包含自己的状态还有自己维护的集群元数据，互相通过ping交换元数据

每个节点每秒都会频繁发送ping给集群中的其他节点，ping，频繁的互相之间交换数据，互相进行元数据的更新

pong: 返回ping和meet，包含自己的状态和其他信息，也可以用于信息广播和更新

fail: 某个节点判断另一个节点fail之后，就发送fail给其他节点，通知其他节点，指定的节点宕机了

3、ping消息深入

ping很频繁，而且要携带一些元数据，所以可能会加重网络负担

每个节点每秒会执行10次ping，每次会选择5个最久没有通信的其他节点

当然如果发现某个节点通信延时达到了cluster_node_timeout / 2，那么立即发送ping，避免数据交换延时过长，落后的时间太长了

比如说，两个节点之间都10分钟没有交换数据了，那么整个集群处于严重的元数据不一致的情况，就会有问题

所以cluster_node_timeout可以调节，如果调节比较大，那么会降低发送的频率

每次ping，一个是带上自己节点的信息，还有就是带上1/10其他节点的信息，发送出去，进行数据交换

至少包含3个其他节点的信息，最多包含总节点-2个其他节点的信息

-------------------------------------------------------------------------------------------------------

### 面向集群的jedis内部实现原理

开发，jedis，redis的java client客户端，redis cluster，jedis cluster api

jedis cluster api与redis cluster集群交互的一些基本原理

1、基于重定向的客户端

redis-cli -c，自动重定向

（1）请求重定向

客户端可能会挑选任意一个redis实例去发送命令，每个redis实例接收到命令，都会计算key对应的hash slot

如果在本地就在本地处理，否则返回moved给客户端，让客户端进行重定向

cluster keyslot mykey，可以查看一个key对应的hash slot是什么

用redis-cli的时候，可以加入-c参数，支持自动的请求重定向，redis-cli接收到moved之后，会自动重定向到对应的节点执行命令

（2）计算hash slot

计算hash slot的算法，就是根据key计算CRC16值，然后对16384取模，拿到对应的hash slot

用hash tag可以手动指定key对应的slot，同一个hash tag下的key，都会在一个hash slot中，比如set mykey1:{100}和set mykey2:{100}

（3）hash slot查找

节点间通过gossip协议进行数据交换，就知道每个hash slot在哪个节点上

2、smart jedis

（1）什么是smart jedis

基于重定向的客户端，很消耗网络IO，因为大部分情况下，可能都会出现一次请求重定向，才能找到正确的节点

所以大部分的客户端，比如java redis客户端，就是jedis，都是smart的

本地维护一份hashslot -> node的映射表，缓存，大部分情况下，直接走本地缓存就可以找到hashslot -> node，不需要通过节点进行moved重定向

（2）JedisCluster的工作原理

在JedisCluster初始化的时候，就会随机选择一个node，初始化hashslot -> node映射表，同时为每个节点创建一个JedisPool连接池

每次基于JedisCluster执行操作，首先JedisCluster都会在本地计算key的hashslot，然后在本地映射表找到对应的节点

如果那个node正好还是持有那个hashslot，那么就ok; 如果说进行了reshard这样的操作，可能hashslot已经不在那个node上了，就会返回moved

如果JedisCluter API发现对应的节点返回moved，那么利用该节点的元数据，更新本地的hashslot -> node映射表缓存

重复上面几个步骤，直到找到对应的节点，如果重试超过5次，那么就报错，JedisClusterMaxRedirectionException

jedis老版本，可能会出现在集群某个节点故障还没完成自动切换恢复时，频繁更新hash slot，频繁ping节点检查活跃，导致大量网络IO开销

jedis最新版本，对于这些过度的hash slot更新和ping，都进行了优化，避免了类似问题

（3）hashslot迁移和ask重定向

如果hash slot正在迁移，那么会返回ask重定向给jedis

jedis接收到ask重定向之后，会重新定位到目标节点去执行，但是因为ask发生在hash slot迁移过程中，所以JedisCluster API收到ask是不会更新hashslot本地缓存

已经可以确定说，hashslot已经迁移完了，moved是会更新本地hashslot->node映射表缓存的

-------------------------------------------------------------------------------------------------------

### 高可用性与主备切换原理

redis cluster的高可用的原理，几乎跟哨兵是类似的

1、判断节点宕机

如果一个节点认为另外一个节点宕机，那么就是pfail，主观宕机

如果多个节点都认为另外一个节点宕机了，那么就是fail，客观宕机，跟哨兵的原理几乎一样，sdown，odown

在cluster-node-timeout内，某个节点一直没有返回pong，那么就被认为pfail

如果一个节点认为某个节点pfail了，那么会在gossip ping消息中，ping给其他节点，如果超过半数的节点都认为pfail了，那么就会变成fail

2、从节点过滤

对宕机的master node，从其所有的slave node中，选择一个切换成master node

检查每个slave node与master node断开连接的时间，如果超过了cluster-node-timeout * cluster-slave-validity-factor，那么就没有资格切换成master
从节点的数据太老了，就不能切换成master
这个也是跟哨兵是一样的，从节点超时过滤的步骤

3、从节点选举

哨兵：对所有从节点进行排序，slave priority，offset，run id

每个从节点，都根据自己对master复制数据的offset，来设置一个选举时间，offset越大（复制数据越多）的从节点，选举时间越靠前，优先进行选举

所有的master node开始slave选举投票，给要进行选举的slave进行投票，如果大部分master node（N/2 + 1）都投票给了某个从节点，那么选举通过，那个从节点可以切换成master

从节点执行主备切换，从节点切换为主节点

4、与哨兵比较

整个流程跟哨兵相比，非常类似，所以说，redis cluster功能强大，直接集成了replication和sentinal的功能

## redis在实践中的一些常见问题以及优化思路（包含linux内核参数优化）

fork耗时导致高并发请求延时

RDB，生成内存快照，和AOF进行rewrite操作都会耗费磁盘IO

主进程fork子进程，子进程是需要拷贝父进程的内存数据，也是会耗费一定的时间的

一般来说，如果父进程内存有1个G的数据，那么fork可能会耗费在20ms左右，如果是10G~30G，那么就会耗费20 * 10，甚至20 * 30，也就是几百毫秒的时间

info stats中的latest_fork_usec，可以看到最近一次fork的时长

redis单机QPS一般在几万，fork可能一下子就会拖慢几万条操作的请求时长，从几毫秒变成1秒

优化思路

fork耗时跟redis主进程的内存有关系，一般控制redis的内存在10GB以内

2、AOF的阻塞问题

redis将数据写入AOF缓冲区，单独开一个线程做fsync操作，每秒一次

但是redis主线程会检查两次fsync的时间，如果距离上次fsync时间超过了2秒，那么写请求就会阻塞

everysec，最多丢失2秒的数据

一旦fsync超过2秒的延时，整个redis就被拖慢

优化思路

优化硬盘写入速度，建议采用SSD，不要用普通的机械硬盘，SSD，大幅度提升磁盘读写的速度

3、主从复制延迟问题

主从复制可能会超时严重，这个时候需要良好的监控和报警机制

在info replication中，可以看到master和slave复制的offset，做一个差值就可以看到对应的延迟量

如果延迟过多，那么就进行报警

4、主从复制风暴问题

如果一下子让多个slave从master去执行全量复制，一份大的rdb同时发送到多个slave，会导致网络带宽被严重占用

如果一个master真的要挂载多个slave，那尽量用树状结构，不要用星型结构

5、vm.overcommit_memory

0: 检查有没有足够内存，没有的话申请内存失败
1: 允许使用内存直到用完为止
2: 内存地址空间不能超过swap + 50%

如果是0的话，可能导致类似fork等操作执行失败，申请不到足够的内存空间

cat /proc/sys/vm/overcommit_memory
echo "vm.overcommit_memory=1" >> /etc/sysctl.conf
sysctl vm.overcommit_memory=1

6、swapiness

cat /proc/version，查看linux内核版本

如果linux内核版本<3.5，那么swapiness设置为0，这样系统宁愿swap也不会oom killer（杀掉进程）
如果linux内核版本>=3.5，那么swapiness设置为1，这样系统宁愿swap也不会oom killer

保证redis不会被杀掉

echo 0 > /proc/sys/vm/swappiness
echo vm.swapiness=0 >> /etc/sysctl.conf

7、最大打开文件句柄

ulimit -n 10032 10032

自己去上网搜一下，不同的操作系统，版本，设置的方式都不太一样

8、tcp backlog

cat /proc/sys/net/core/somaxconn
echo 511 > /proc/sys/net/core/somaxconn

### redis企业级解决方案

1、redis的第一套企业级的架构

如果你的数据量不大，单master就可以容纳，一般来说你的缓存的总量在10G以内就可以，那么建议按照以下架构去部署redis

redis持久化+备份方案+容灾方案+replication（主从+读写分离）+sentinal（哨兵集群，3个节点，高可用性）

可以支撑的数据量在10G以内，可以支撑的写QPS在几万左右，可以支撑的读QPS可以上10万以上（随你的需求，水平扩容slave节点就可以），可用性在99.99%

2、redis的第二套企业级架构

如果你的数据量很大，数据量是很大的

海量数据

redis cluster

多master分布式存储数据，水平扩容

支撑更多的数据量，1T+以上没问题，只要扩容master即可

读写QPS分别都达到几十万都没问题，只要扩容master即可，redis cluster，读写分离，支持不太好，readonly才能去slave上读

支撑99.99%可用性，也没问题，slave -> master的主备切换，冗余slave去进一步提升可用性的方案（每个master挂一个slave，但是整个集群再加个3个slave冗余一下）

### LRU算法概述

redis默认情况下就是使用LRU策略的，因为内存是有限的，但是如果你不断地往redis里面写入数据，那肯定是没法存放下所有的数据在内存的

所以redis默认情况下，当内存中写入的数据很满之后，就会使用LRU算法清理掉部分内存中的数据，腾出一些空间来，然后让新的数据写入redis缓存中

LRU：Least Recently Used，最近最少使用算法

将最近一段时间内，最少使用的一些数据，给干掉。比如说有一个key，在最近1个小时内，只被访问了一次; 还有一个key在最近1个小时内，被访问了1万次

这个时候比如你要将部分数据给清理掉，你会选择清理哪些数据啊？肯定是那个在最近小时内被访问了1万次的数据

### 缓存清理设置

redis.conf

maxmemory，设置redis用来存放数据的最大的内存大小，一旦超出这个内存大小之后，就会立即使用LRU算法清理掉部分数据

如果用LRU，那么就是将最近最少使用的数据从缓存中清除出去

对于64 bit的机器，如果maxmemory设置为0，那么就默认不限制内存的使用，直到耗尽机器中所有的内存为止; 但是对于32 bit的机器，有一个隐式的闲置就是3GB

maxmemory-policy，可以设置内存达到最大闲置后，采取什么策略来处理

（1）noeviction: 如果内存使用达到了maxmemory，client还要继续写入数据，那么就直接报错给客户端
（2）allkeys-lru: 就是我们常说的LRU算法，移除掉最近最少使用的那些keys对应的数据
（3）volatile-lru: 也是采取LRU算法，但是仅仅针对那些设置了指定存活时间（TTL）的key才会清理掉
（4）allkeys-random: 随机选择一些key来删除掉
（5）volatile-random: 随机选择一些设置了TTL的key来删除掉
（6）volatile-ttl: 移除掉部分keys，选择那些TTL时间比较短的keys

在redis里面，写入key-value对的时候，是可以设置TTL，存活时间，比如你设置了60s。那么一个key-value对，在60s之后就会自动被删除

redis的使用，各种数据结构，list，set，等等

allkeys-lru

这边拓展一下思路，对技术的研究，一旦将一些技术研究的比较透彻之后，就喜欢横向对比底层的一些原理

storm，科普一下

玩儿大数据的人搞得，领域，实时计算领域，storm

storm有很多的流分组的一些策略，按shuffle分组，global全局分组，direct直接分组，fields按字段值hash后分组

分组策略也很多，但是，真正公司里99%的场景下，使用的也就是shuffle和fields，两种策略

redis，给了这么多种乱七八糟的缓存清理的算法，其实真正常用的可能也就那么一两种，allkeys-lru是最常用的

### 缓存清理的流程

（1）客户端执行数据写入操作
（2）redis server接收到写入操作之后，检查maxmemory的限制，如果超过了限制，那么就根据对应的policy清理掉部分数据
（3）写入操作完成执行

### redis的LRU近似算法

科普一个相对来说稍微高级一丢丢的知识点

redis采取的是LRU近似算法，也就是对keys进行采样，然后在采样结果中进行数据清理

redis 3.0开始，在LRU近似算法中引入了pool机制，表现可以跟真正的LRU算法相当，但是还是有所差距的，不过这样可以减少内存的消耗

redis LRU算法，是采样之后再做LRU清理的，跟真正的、传统、全量的LRU算法是不太一样的

maxmemory-samples，比如5，可以设置采样的大小，如果设置为10，那么效果会更好，不过也会耗费更多的CPU资源

## redis分布式锁

官方叫做RedLock算法，是redis官方支持的分布式锁算法。

这个分布式锁有3个重要的考量点，互斥（只能有一个客户端获取锁），不能死锁，容错（大部分redis节点或者这个锁就可以加可以释放）

第一个最普通的实现方式，如果就是在redis里创建一个key算加锁（根据一个key是否创建成功来判断是否获取到了锁）

SET my:lock 随机值 NX PX 30000，这个命令就ok，这个的NX的意思就是只有key不存在的时候才会设置成功，PX 30000的意思是30秒后锁自动释放。别人创建的时候如果发现已经有了就不能加锁了。

释放锁就是删除key，但是一般可以用lua脚本删除，判断value一样才删除：

关于redis如何执行lua脚本，自行百度

if redis.call("get",KEYS[1]) == ARGV[1] then
return redis.call("del",KEYS[1])
else
    return 0
end

为啥要用随机值呢？因为如果某个客户端获取到了锁，但是阻塞了很长时间才执行完，此时可能已经自动释放锁了，此时可能别的客户端已经获取到了这个锁，要是你这个时候直接删除key的话会有问题，所以得用随机值加上面的lua脚本来释放锁。

但是这样是肯定不行的。因为如果是普通的redis单实例，那就是单点故障。或者是redis普通主从，那redis主从异步复制，如果主节点挂了，key还没同步到从节点，此时从节点切换为主节点，别人就会拿到锁。

第二个问题，RedLock算法

这个场景是假设有一个redis cluster，有5个redis master实例。然后执行如下步骤获取一把锁：

1）获取当前时间戳，单位是毫秒
2）跟上面类似，轮流尝试在每个master节点上创建锁，过期时间较短，一般就几十毫秒
3）尝试在大多数节点上建立一个锁，比如5个节点就要求是3个节点（n / 2 +1）
4）客户端计算建立好锁的时间，如果建立锁的时间小于超时时间，就算建立成功了
5）要是锁建立失败了，那么就依次删除这个锁
6）只要别人建立了一把分布式锁，你就得不断轮询去尝试获取锁