package cn.cjf.chat.handle;

import cn.cjf.chat.domain.Packet;
import cn.cjf.chat.utils.SerializableUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author CJF
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out) {
        SerializableUtil.encode(out, packet);
    }
}
