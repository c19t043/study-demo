package cn.cjf.netty.server;

import cn.cjf.netty.handle.FilterHandle;
import cn.cjf.netty.handle.PacketCodecHandler;
import cn.cjf.netty.server.handler.AuthHandler;
import cn.cjf.netty.server.handler.IMIdleStateHandler;
import cn.cjf.netty.server.handler.LoginRequestHandler;
import cn.cjf.netty.server.handler.SimpleServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.Date;

/**
 * NettyServer class
 *
 * @author cjf
 * @date 2018/10/09
 */
public class NettyServer {
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
//                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new IMIdleStateHandler());
                        ch.pipeline().addLast(new FilterHandle());
                        ch.pipeline().addLast(PacketCodecHandler.INSTANCE);
                        ch.pipeline().addLast(LoginRequestHandler.INSTANCE);
//                        ch.pipeline().addLast(AuthHandler.INSTANCE);
                        ch.pipeline().addLast(SimpleServerHandler.INSTANCE);
                    }
                });
        try {
            bind(bootstrap, PORT);
        } finally {
//            boss.shutdownGracefully();
//            worker.shutdownGracefully();
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
        new NettyServer().start(PORT);
    }
}
