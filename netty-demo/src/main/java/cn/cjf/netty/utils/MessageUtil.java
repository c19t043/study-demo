package cn.cjf.netty.utils;

import cn.cjf.netty.client.handler.SimpleClientHandler;
import cn.cjf.netty.domain.Packet;
import cn.cjf.netty.server.session.SessionGroupManager;
import cn.cjf.netty.server.session.SessionManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * @author CJF
 */
public class MessageUtil {

    /**
     * 客户端向服务端同步发送消息
     *
     * @param channel 通道
     * @param packet  消息
     */
    public static void clientSendMsg2ServerWithSync(Channel channel, Packet packet) {
        //发送消息
        String messageId = UUID.randomUUID().toString();

        sendMessage(channel, packet, messageId);

        //同步等待服务端响应
        CountDownLatch count = new CountDownLatch(1);
        SimpleClientHandler.addCountDownLatch(messageId, count);
        try {
            count.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 服务端发送消息给客户端
     *
     * @param srcUserId 消息来源通道
     * @param groupId   发送的目标群组
     * @param packet    向客户端发送的消息
     * @param messageId 消息唯一id
     */
    public static void serverSendMessage2Group(String srcUserId, String groupId, Packet packet, String messageId) {
        if (packet != null) {
            Set<String> groupMapping = SessionGroupManager.getInstance().getGroupMapping(groupId);
            if (groupMapping != null && !groupMapping.isEmpty()) {
                for (String userId : groupMapping) {
                    if (!userId.equals(srcUserId)) {
                        Channel channel = SessionManager.getInstance().getChannel(userId);
                        sendMessage(channel, packet, messageId);
                    }
                }
            }
        }
    }

    /**
     * 服务端发送消息给指定的客户端
     *
     * @param userId 用户id
     * @param packet 数据包
     */
    public static void serverSendMessage2User(String userId, Packet packet) {
        Channel channel = SessionManager.getInstance().getChannel(userId);

        sendMessage(channel, packet);
    }

    /**
     * 服务端响应消息给指定的客户端
     *
     * @param userId 用户id
     * @param packet 数据包
     */
    public static void serverSendMessage2User(String userId, Packet packet, String messageId) {
        Channel channel = SessionManager.getInstance().getChannel(userId);

        sendMessage(channel, packet, messageId);
    }

    /**
     * 发送消息
     *
     * @param channel 通道
     * @param packet  数据包
     */
    public static void sendMessage(Channel channel, Packet packet) {
        String messageId = UUID.randomUUID().toString();

        sendMessage(channel, packet, messageId);
    }

    /**
     * 发送消息
     *
     * @param channel   通道
     * @param packet    数据包
     * @param messageId 消息唯一id
     */
    public static void sendMessage(Channel channel, Packet packet, String messageId) {
        ByteBuf buffer = channel.alloc().buffer();
        packet.setMessageId(messageId);
        SerializableUtil.encode(buffer, packet);
        channel.writeAndFlush(buffer);
    }
}
