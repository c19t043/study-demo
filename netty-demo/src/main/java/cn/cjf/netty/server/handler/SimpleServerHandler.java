package cn.cjf.chat.server.handler;

import cn.cjf.chat.config.PacketType;
import cn.cjf.chat.domain.Packet;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author CJF
 */
@ChannelHandler.Sharable
public class SimpleServerHandler extends SimpleChannelInboundHandler<Packet> {
    public static final SimpleServerHandler INSTANCE = new SimpleServerHandler();

    private Map<Byte, SimpleChannelInboundHandler<? extends Packet>> handlerMap;

    private SimpleServerHandler() {
        handlerMap = new HashMap<>();

        handlerMap.put(PacketType.MESSAGE_PACKET, MessageRequestHandler.INSTANCE);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        handlerMap.get(packet.getType()).channelRead(ctx, packet);
    }
}
