package cn.cjf.netty.client;

import cn.cjf.netty.client.commandhandle.FacadeHandle;
import cn.cjf.netty.client.handler.HeartBeatTimerHandler;
import cn.cjf.netty.client.handler.SimpleClientHandler;
import cn.cjf.netty.config.CommandEnum;
import cn.cjf.netty.handle.FilterHandle;
import cn.cjf.netty.handle.PacketDecoder;
import cn.cjf.netty.handle.PacketEncoder;
import cn.cjf.netty.thread.ThreadPoolUtil;
import cn.cjf.netty.utils.ChannelBindKeyUtil;
import cn.cjf.netty.utils.ConsoleUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author cjf
 * @date 2018/10/09
 */
public class NettyClient {
    private static final int MAX_RETRY = 5;
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 7000;

    private void connect(String host, int port) {
        NioEventLoopGroup worker = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(worker)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
//                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new HeartBeatTimerHandler());
                        ch.pipeline().addLast(new FilterHandle());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new SimpleClientHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });
        try {
            connect(bootstrap, host, port, MAX_RETRY);
        } finally {
//            reConnectServer();
        }
    }

    /**
     * 断线重连
     */
    private void reConnectServer() {
        try {
            Thread.sleep(5000);
            System.err.println("客户端进行断线重连");
            connect(HOST, PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retryCount) {
        bootstrap.connect(host, port).addListener(new GenericFutureListener() {
            @Override
            public void operationComplete(Future future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println(new Date() + ": 连接成功，启动控制台线程……");
                    Channel channel = ((ChannelFuture) future).channel();
                    startConsoleThread(channel);
                } else if (retryCount == 0) {
                    System.err.println("重试次数已用完，放弃连接！");
                } else {
                    // 第几次重连
                    int order = (MAX_RETRY - retryCount) + 1;
                    // 本次重连的间隔
                    int delay = 1 << order;
                    System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                    bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retryCount - 1), delay, TimeUnit
                            .SECONDS);
                }
            }
        });
    }

    private static void startConsoleThread(Channel channel) {
        ThreadPoolUtil.getInstance().executeTask(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    String[] messages;
                    if (ChannelBindKeyUtil.hasLogin(channel)) {
                        String loginUserId = ChannelBindKeyUtil.getLoginUserId(channel);
                        String hintInfo = "[" + loginUserId + "]请输入要执行的命令：--》";
                        messages = ConsoleUtil.consoleInAndParse(hintInfo);
                    } else {
                        messages = new String[2];
                        messages[0] = CommandEnum.AUTH_LOGIN.toString();
                    }
                    FacadeHandle.handle(channel, messages[0], messages[1]);
                }
            }
        });
    }

    public static void main(String[] args) {
        new NettyClient().connect(HOST, PORT);
    }
}

