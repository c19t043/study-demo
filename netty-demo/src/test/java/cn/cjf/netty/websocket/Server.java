package cn.cjf.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class Server {
    public static void main(String[] args) {
        new Server().start(8000);
    }

    public void start(int port) {
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        //将请求和应答解码为http消息
                        sc.pipeline().addLast("http-codec",new HttpServerCodec());
                        //将http消息的多个部分成一条完整的HTTP消息
                        sc.pipeline().addLast("aggregator",new HttpObjectAggregator(65536));
                        //向客户端发送html5文件
                        sc.pipeline().addLast("http-chunked",new ChunkedWriteHandler());
                        //用于处理websocket, /ws为访问websocket时的uri
                        sc.pipeline().addLast("webSocketServerProtocolHandler", new WebSocketServerProtocolHandler("/ws"));
                        //在管道中添加自己的接受数据后的实现
                        sc.pipeline().addLast(new MyWebSocketServerHandler());
                    }
                });
        try {
            ChannelFuture future = bootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
