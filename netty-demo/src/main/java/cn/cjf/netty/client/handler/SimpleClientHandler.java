package cn.cjf.netty.client.handler;

import cn.cjf.netty.config.ConstantConfig;
import cn.cjf.netty.config.MessagePattern;
import cn.cjf.netty.domain.LoginEchoPacket;
import cn.cjf.netty.domain.MessagePacket;
import cn.cjf.netty.domain.Packet;
import cn.cjf.netty.utils.ChannelBindKeyUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * @author CJF
 */
public class SimpleClientHandler extends ChannelInboundHandlerAdapter {

    private static Map<String, CountDownLatch> countDownLatchMap = new ConcurrentHashMap<>(ConstantConfig.MAP_INITIAL_CAPACITY);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Packet packet = (Packet) msg;

        System.out.println("收到服务端：" + ctx.channel().remoteAddress() + " ，发送内容： -->" + packet);

        String messageId;
        if (packet instanceof LoginEchoPacket) {
            LoginEchoPacket loginEchoPacket = (LoginEchoPacket) packet;
            messageId = loginEchoPacket.getMessageId();

            ChannelBindKeyUtil.bindLoginKey(ctx.channel());
        } else {
            MessagePacket messagePacket = (MessagePacket) packet;
            messageId = messagePacket.getMessageId();

            if (messagePacket.getMessageSrcType() == MessagePattern.MESSAGE_SRC_TYPE_SINGLE) {
                String hintInfo = "请输入要聊天的内容；格式：要聊天的用【userId:[用户id]:消息】，回复消息格式【!@userId:[用户id]:消息】";
                System.out.println(hintInfo);
            } else if (messagePacket.getMessageSrcType() == MessagePattern.MESSAGE_SRC_TYPE_GROUP) {
                String hintInfo = "请输入要聊天的内容；格式：要聊天的用【groupId:[群组id]:消息】，回复消息格式【!@groupId:[群组id]:消息】";
                System.out.println(hintInfo);
            }
        }

        if (countDownLatchMap.containsKey(messageId)) {
            CountDownLatch count = countDownLatchMap.get(messageId);
            count.countDown();
            countDownLatchMap.remove(messageId);
        }
    }

    public static void addCountDownLatch(String messageId, CountDownLatch countDownLatch) {
        countDownLatchMap.put(messageId, countDownLatch);
    }
}
