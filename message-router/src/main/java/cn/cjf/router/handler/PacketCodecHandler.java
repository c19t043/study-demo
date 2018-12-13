package cn.cjf.router.handler;

import cn.cjf.router.config.ConstantConf;
import cn.cjf.router.protocal.Packet;
import cn.cjf.router.utils.SerializableUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

/**
 * @author CJF
 */
@ChannelHandler.Sharable
public class PacketCodecHandler extends MessageToMessageCodec<ByteBuf, Packet> {
    public static final PacketCodecHandler INSTANCE = new PacketCodecHandler();

    private PacketCodecHandler() {

    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) {
        byteBuf.markReaderIndex();
        byteBuf.skipBytes(ConstantConf.INT_BYTE * 2);
        int algorithm = byteBuf.getInt(ConstantConf.INT_BYTE);
        byteBuf.resetReaderIndex();
        out.add(SerializableUtil.decode(byteBuf, algorithm));
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, List<Object> out) {
        ByteBuf byteBuf = SerializableUtil.encode(packet, packet.getAlgorithm());
        out.add(byteBuf);
    }
}