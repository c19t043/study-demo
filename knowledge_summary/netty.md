# netty
## ChannelOption选项解释
这个都是socket的标准参数，并不是netty自己的。

具体为：

ChannelOption.SO_BACKLOG, 1024

       BACKLOG用于构造服务端套接字ServerSocket对象，标识当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度。如果未设置或所设置的值小于1，Java将使用默认值50。

 

ChannelOption.SO_KEEPALIVE, true

      是否启用心跳保活机制。在双方TCP套接字建立连接后（即都进入ESTABLISHED状态）并且在两个小时左右上层没有任何数据传输的情况下，这套机制才会被激活。

 

ChannelOption.TCP_NODELAY, true

 在TCP/IP协议中，无论发送多少数据，总是要在数据前面加上协议头，同时，对方接收到数据，也需要发送ACK表示确认。为了尽可能的利用网络带宽，TCP总是希望尽可能的发送足够大的数据。这里就涉及到一个名为Nagle的算法，该算法的目的就是为了尽可能发送大块数据，避免网络中充斥着许多小数据块。

 TCP_NODELAY就是用于启用或关于Nagle算法。如果要求高实时性，有数据发送时就马上发送，就将该选项设置为true关闭Nagle算法；如果要减少发送次数减少网络交互，就设置为false等累积一定大小后再发送。默认为false。

 

4.ChannelOption.SO_REUSEADDR, true

SO_REUSEADDR允许启动一个监听服务器并捆绑其众所周知端口，即使以前建立的将此端口用做他们的本地端口的连接仍存在。这通常是重启监听服务器时出现，若不设置此选项，则bind时将出错。
SO_REUSEADDR允许在同一端口上启动同一服务器的多个实例，只要每个实例捆绑一个不同的本地IP地址即可。对于TCP，我们根本不可能启动捆绑相同IP地址和相同端口号的多个服务器。
SO_REUSEADDR允许单个进程捆绑同一端口到多个套接口上，只要每个捆绑指定不同的本地IP地址即可。这一般不用于TCP服务器。
SO_REUSEADDR允许完全重复的捆绑：当一个IP地址和端口绑定到某个套接口上时，还允许此IP地址和端口捆绑到另一个套接口上。一般来说，这个特性仅在支持多播的系统上才有，而且只对UDP套接口而言（TCP不支持多播）

5.ChannelOption.SO_RCVBUF  AND  ChannelOption.SO_SNDBUF 
定义接收或者传输的系统缓冲区buf的大小，

6.ChannelOption.ALLOCATOR 
Netty4使用对象池，重用缓冲区
bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
bootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

## Netty解决半包（TCP粘包/拆包导致）读写问题
摘要
使用Netty进行异步IO编程，同事问我粘包/拆包问题如何处理，所以抽空分析一下TCP粘包/拆包问题的产生；Netty提供了多种编解码器用于处理半包问题，熟练掌握了类库的应用，TCP粘包问题变得很容易。
TCP粘包/拆包

TCP是个"流"协议，所谓流，就是没有界限没有分割的一串数据。TCP会根据缓冲区的实际情况进行包划分，一个完整的包可能会拆分成多个包进行发送，也用可能把多个小包封装成一个大的数据包发送。这就是TCP粘包/拆包。
问题产生的原因：

1-应用程序write写入的字节大小 大于 套接字发送缓冲区大小

2-进行MSS大小的TCP分段

3-以太网帧的payload大于MTU进行IP分片

TCP粘包/拆包解决办法

1-定长消息，例如每个报文长度固定，不够补空格

2-使用回车换行符分割，在包尾加上分割符，例如Ftp协议

3-消息分割，头为长度（消息总长度或消息体长度），通常头用一个int32表示

4-复杂的应用层协议

Netty对于读写半包的的处理

提供多种解码器用于处理半包，如 LineBasedFrameDecoder、DelimiterBasedFrameDecoder、FixedLengthFrameDecoder、ProtobufVarint32FrameDecoder、ByteToMessageDecoder以及LengthFileldBasedFrameDecoder等等。

//decoder
//1-读半包的解码器
ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 4)); 
//2-进行消息解码
ch.pipeline().addLast(new ProtobufDecoder(BoxAuthReqProto.AuthRequest.getDefaultInstance()));
//encoder					
ch.pipeline().addLast(new LengthFieldPrepender(4));
ch.pipeline().addLast(new ProtobufEncoder());

netty 粘包问题处理
key words: netty 粘包 解包 半包 TCP

一般TCP粘包/拆包解决办法
定长消息，例如每个报文长度固定，不够补空格
使用回车换行符分割，在包尾加上分割符，例如Ftp协议
消息分割，头为长度（消息总长度或消息体长度），通常头用一个int32表示
复杂的应用层协议

netty的几种解决方案
特殊分隔符解码器：DelimiterBasedFrameDecoder
客户端发送消息

String message = "netty is a nio server framework &"
                +"which enables quick and easy development &"
                +"of net applications such as protocol &"
                +"servers and clients!";

服务端添加解码器：DelimiterBasedFrameDecoder

ByteBuf delimiter = Unpooled.copiedBuffer("&".getBytes());
ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,delimiter));
//1024表示单条消息的最大长度，解码器在查找分隔符的时候，达到该长度还没找到的话会抛异常
ch.pipeline().addLast(new StringDecoder());
....
ch.pipeline().addLast(new StringEncoder());

打印输出：

接收消息：[netty is a nio server framework ]
接收消息：[which enables quick and easy development ]
接收消息：[of net applications such as protocol]
接收消息：[servers and clients!]

参数解释：

public DelimiterBasedFrameDecoder(int maxFrameLength, ByteBuf delimiter) {
    this(maxFrameLength, true, delimiter);
}
maxFrameLength：解码的帧的最大长度
 
stripDelimiter：解码时是否去掉分隔符
 
failFast：为true，当frame长度超过maxFrameLength时立即报TooLongFrameException异常，为false，读取完整个帧再报异常
 
delimiter：分隔符


定长解码器：FixedLengthFrameDecoder
参数说明：

frameLength：帧的固定长度
服务端

ch.pipeline().addLast(new FixedLengthFrameDecoder(30));//设置定长解码器 长度设置为30
 
 
public void channelRead(ChannelHandlerContext ctx, Object msg)
        throws Exception {
    System.out.println("接收客户端msg:["+msg+"]");
    ByteBuf echo=Unpooled.copiedBuffer(MESSAGE.getBytes());
    ctx.writeAndFlush(echo);
}


客户端

ch.pipeline().addLast(new FixedLengthFrameDecoder(30));//设置定长解码器


基于包头不固定长度的解码器：LengthFieldBasedFrameDecoder
参数说明

maxFrameLength：解码的帧的最大长度
lengthFieldOffset：长度属性的起始位（偏移位），包中存放有整个大数据包长度的字节，这段字节的其实位置
lengthFieldLength：长度属性的长度，即存放整个大数据包长度的字节所占的长度
lengthAdjustmen：长度调节值，在总长被定义为包含包头长度时，修正信息长度。
initialBytesToStrip：跳过的字节数，根据需要我们跳过lengthFieldLength个字节，以便接收端直接接受到不含“长度属性”的内容
failFast ：为true，当frame长度超过maxFrameLength时立即报TooLongFrameException异常，为false，读取完整个帧再报异常
备注：如果长度解析失误，（过大，就直接丢弃这个包；过小，1、netty不抛出异常；2、校验通不过）

源码：
int frameLengthInt = (int) frameLength;
if (in.readableBytes() < frameLengthInt) {
    return null;
}

封包时配合使用LengthFieldPrepender，很容易加上包长度

包头添加总包长度字节：LengthFieldPrepender
在发布时，自动在帧的头部加上长度
参数说明：

lengthFieldLength：长度属性的字节长度
lengthIncludesLengthFieldLength：false，长度字节不算在总长度中，true，算到总长度中

应用：
pipeline.addLast("frameEncode", new LengthFieldPrepender(4, false));

官方说明：

编码类，自动将
+----------------+  
| "HELLO, WORLD" |  
+----------------+
 
格式的数据转换成如下格式的数据，    
+--------+----------------+ 
+ 0x000C | "HELLO, WORLD" | 
+--------+----------------+
 
如果lengthIncludesLengthFieldLength设置为true,则编码为（多了两个字节）
+--------+----------------+ 
+ 0x000E | "HELLO, WORLD" | 
+--------+----------------+
 
 

