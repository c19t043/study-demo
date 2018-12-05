# redis
## 不到 10 个提升逼格的 Redis 命令
+ keys

我把这个命令放在第一位，是因为笔者曾经做过的项目，以及一些朋友的项目，都因为使用keys这个命令，导致出现性能毛刺。这个命令的时间复杂度是O(N)，而且redis又是单线程执行，在执行keys时即使是时间复杂度只有O(1)例如SET或者GET这种简单命令也会堵塞，从而导致这个时间点性能抖动，甚至可能出现timeout。
> 强烈建议生产环境屏蔽keys命令（后面会介绍如何屏蔽）。

+ scan

既然keys命令不允许使用，那么有什么代替方案呢？有！那就是scan命令。如果把keys命令比作类似select * from users where username like '%afei%'这种SQL，那么scan应该是select * from users where id>? limit 10这种命令。

官方文档用法如下：
> `SCAN cursor [MATCH pattern] [COUNT count]`

初始执行scan命令例如scan 0。SCAN命令是一个基于游标的迭代器。这意味着命令每次被调用都需要使用上一次这个调用返回的游标作为该次调用的游标参数，以此来延续之前的迭代过程。当SCAN命令的游标参数被设置为0时，服务器将开始一次新的迭代，而当redis服务器向用户返回值为0的游标时，表示迭代已结束，这是唯一迭代结束的判定方式，而不能通过返回结果集是否为空判断迭代结束。

使用方式：
```
127.0.0.1:6380> scan 0
1) "22"
2)  1) "23"
    2) "20"
    3) "14"
    4) "2"
    5) "19"
    6) "9"
    7) "3"
    8) "21"
    9) "12"
   10) "25"
   11) "7"
```
返回结果分为两个部分：第一部分即1)就是下一次迭代游标，第二部分即2)就是本次迭代结果集。

+ slowlog

上面提到不能使用keys命令，如果就有开发这么做了呢，我们如何得知？与其他任意存储系统例如mysql，mongodb可以查看慢日志一样，redis也可以，即通过命令`slowlog`。用法如下：
> `SLOWLOG subcommand [argument]`

subcommand主要有：
```
- get，用法：slowlog get [argument]，获取argument参数指定数量的慢日志。
- len，用法：slowlog len，总慢日志数量。
- reset，用法：slowlog reset，清空慢日志。
```
执行结果如下：
```
127.0.0.1:6380> slowlog get 5
1) 1) (integer) 2
   2) (integer) 1532656201
   3) (integer) 2033
   4) 1) "flushddbb"
2) 1) (integer) 1  ----  慢日志编码，一般不用care
   2) (integer) 1532646897  ----  导致慢日志的命令执行的时间点，如果api有timeout，可以通过对比这个时间，判断可能是慢日志命令执行导致的
   3) (integer) 26424  ----  导致慢日志执行的redis命令，通过4)可知，执行config rewrite导致慢日志，总耗时26ms+
   4) 1) "config"
      2) "rewrite"
```
> 命令耗时超过多少才会保存到slowlog中，可以通过命令config set slowlog-log-slower-than 2000配置并且不需要重启redis。注意：单位是微妙，2000微妙即2毫秒。
