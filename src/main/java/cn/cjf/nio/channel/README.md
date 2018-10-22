# channel

Java NIO的通道类似流，但又有些不同：

既可以从通道中读取数据，又可以写数据到通道。但流的读写通常是单向的。
通道可以异步地读写。
通道中的数据总是要先读到一个Buffer，或者总是要从一个Buffer中写入。
正如上面所说，从通道读取数据到缓冲区，从缓冲区写入数据到通道。如下图所示：

![Channel示意图](/cn/cjf/nio/channel/overview-channels-buffers.png)