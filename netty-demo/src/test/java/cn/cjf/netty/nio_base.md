# nio base
+ java nio (non blocking io) 非阻塞io
+ java aio 异步io
~~~
同步和异步的区别：数据拷贝阶段是否完全由操作系统处理
阻塞和非阻塞操作：是针对发起io请求操作后，是否立即返回一个标志信息而不让请求线程等待
~~~
> nio nio 是同步且非阻塞的io
+ nio和bio对比

| nio | bio |
| --- | ---- |
| 基于缓冲区(buffer) | 基于流(stream) |
| 非阻塞io | 阻塞io|
| 选择器（selector）|无|

+ 基于buffer与基于stream
bio是面向字节流或字符流，而在nio中，它摒弃了传统的io流，
而引入channel和buffer的概念；从channel中读取数据到buffer中，
或将数据从buffer中写到channel中

## 关于 Direct Buffer 和 Non-Direct Buffer 的区别
~~~
Direct Buffer:
所分配的内存不在 JVM 堆上, 不受 GC 的管理.(但是 Direct Buffer 的 Java 对象是由 GC 管理的, 因此当发生 GC, 对象被回收时, Direct Buffer 也会被释放)
因为 Direct Buffer 不在 JVM 堆上分配, 因此 Direct Buffer 对应用程序的内存占用的影响就不那么明显(实际上还是占用了这么多内存, 但是 JVM 不好统计到非 JVM 管理的内存.)
申请和释放 Direct Buffer 的开销比较大. 因此正确的使用 Direct Buffer 的方式是在初始化时申请一个 Buffer, 然后不断复用此 buffer, 在程序结束后才释放此 buffer.
使用 Direct Buffer 时, 当进行一些底层的系统 IO 操作时, 效率会比较高, 因为此时 JVM 不需要拷贝 buffer 中的内存到中间临时缓冲区中.

Non-Direct Buffer:
直接在 JVM 堆上进行内存的分配, 本质上是 byte[] 数组的封装.
因为 Non-Direct Buffer 在 JVM 堆中, 因此当进行操作系统底层 IO 操作中时, 会将此 buffer 的内存复制到中间临时缓冲区中. 因此 Non-Direct Buffer 的效率就较低.
~~~