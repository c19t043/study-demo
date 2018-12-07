package cn.cjf.netty.server.handler;

import cn.cjf.netty.config.MessagePattern;
import cn.cjf.netty.domain.MessagePacket;
import cn.cjf.netty.utils.ChannelBindKeyUtil;
import cn.cjf.netty.utils.MessageUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.lang3.StringUtils;

/**
 * @author CJF
 */
@ChannelHandler.Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessagePacket> {
    public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();

    private MessageRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessagePacket messagePacket) {
        String srcMessageId = messagePacket.getMessageId();
        String srcUserId = ChannelBindKeyUtil.getLoginUserId(ctx.channel());
        //服务端回复：收到消息，原样返回消息
        MessageUtil.serverSendMessage2User(srcUserId, new MessagePacket("收到消息--》" + messagePacket.getMessage()), srcMessageId);

        /*
            解析客户端发送到服务端的消息
            1、userId:，开头，为单聊发送给指定客户端
            2、groupId:，开头，为群聊消息，发送给群聊用户
            3、!@userId:，开头，为单聊回复消息
            4、!@groupId:，开头，为群聊回复消息
         */
        String message = messagePacket.getMessage();
        int msgSplitFirstIndex = message.indexOf(MessagePattern.MESSAGE_SPLIT);
        int msgSplitIndex = message.lastIndexOf(MessagePattern.MESSAGE_SPLIT);

        String userId = null;
        String groupId = null;
        String msgData = message.substring(msgSplitIndex + 1);
        if (message.startsWith(MessagePattern.SINGLE_CHAT_PRE) || message.startsWith(MessagePattern.SINGLE_CHAT_RESPONSE_PRE)) {
            userId = message.substring(msgSplitFirstIndex + 1, msgSplitIndex);
        } else if (message.startsWith(MessagePattern.GROUP_CHAT_PRE) || message.startsWith(MessagePattern.GROUP_CHAT_RESPONSE_PRE)) {
            groupId = message.substring(msgSplitFirstIndex + 1, msgSplitIndex);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("来至用户[").append(srcUserId).append("]的消息--->").append(msgData);
        if (StringUtils.isNotBlank(userId)) {
            //如果是单聊，发送给指定用户
            MessageUtil.serverSendMessage2User(userId, new MessagePacket(sb.toString(), MessagePattern.MESSAGE_SRC_TYPE_SINGLE));
        } else if (StringUtils.isNotBlank(groupId)) {
            //如果是群聊，发送给指定群组
            MessageUtil.serverSendMessage2Group(userId, groupId, new MessagePacket(sb.toString(), MessagePattern.MESSAGE_SRC_TYPE_GROUP), srcMessageId);
        }
    }
}