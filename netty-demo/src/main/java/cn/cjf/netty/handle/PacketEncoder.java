package cn.cjf.netty.handle;

import cn.cjf.netty.domain.Packet;
import cn.cjf.netty.utils.SerializableUtil;
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
