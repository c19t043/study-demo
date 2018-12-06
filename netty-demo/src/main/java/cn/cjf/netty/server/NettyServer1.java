package cn.cjf.chat.server;

import cn.cjf.chat.handle.FilterHandle;
import cn.cjf.chat.handle.PacketCodecHandler;
import cn.cjf.chat.server.handler.AuthHandler;
import cn.cjf.chat.server.handler.IMIdleStateHandler;
import cn.cjf.chat.server.handler.LoginRequestHandler;
import cn.cjf.chat.server.handler.SimpleServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.util.Date;

/**
 * NettyServer class
 *
 * @author cjf
 * @date 2018/10/09
 */
public class NettyServer1 {
    private static final int PORT = 8000;

    private void start(int port) {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        //HttpServerCodec,将请求或应答消息编码或解码为http消息
                        ch.pipeline().addLast("http-codec", new HttpServerCodec());
                        //将http消息的多个部分组合成一条完整的http消息
                        ch.pipeline().addLast("aggregate", new HttpObjectAggregator(65536));
                        //向客户端发送html5文件，主要是用于支持浏览器与服务端进行webSocket通信
                        ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
                        //webSocket服务端处理
                        ch.pipeline().addLast("handler",new WebSocketServerHandler());
                    }
                });
        try {
            bind(bootstrap, PORT);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    private static void bind(ServerBootstrap serverBootstrap, int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + ": 端口[" + port + "]绑定成功!");
            } else {
                System.err.println("端口[" + port + "]绑定失败!");
            }
        });
    }

    public static void main(String[] args) {
        new NettyServer1().start(PORT);
    }
}
