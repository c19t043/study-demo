package cn.cjf.netty.source_read;

import java.io.Closeable;
import java.io.IOException;

/**
 * Channel 在 Java 中，作为一个接口，java.nio.channels.Channel ，定义了 IO 操作的连接与关闭。
 * Channel 有非常多的实现类，最为重要的四个 Channel 实现类如下：
 * SocketChannel ：一个客户端用来发起 TCP 的 Channel 。
 * ServerSocketChannel ：一个服务端用来监听新进来的连接的 TCP 的 Channel 。对于每一个新进来的连接，都会创建一个对应的 SocketChannel 。
 * DatagramChannel ：通过 UDP 读写数据。
 * FileChannel ：从文件中，读写数据。
 */
public interface Channel extends Closeable {
    /**
     * 判断此通道是否处于打开状态。 
     */
    public boolean isOpen();

    /**
     *关闭此通道。
     */
    public void close() throws IOException;

}