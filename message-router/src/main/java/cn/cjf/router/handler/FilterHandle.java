package cn.cjf.router.handler;

import cn.cjf.router.config.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 魔法数字（int）+版本号(int)+路由算法(int)+数据长度(int)
 */
public class FilterHandle extends LengthFieldBasedFrameDecoder {
    private static final int LENGTH_FIELD_OFFSET = 12;
    private static final int LENGTH_FIELD_LENGTH = 4;

    public FilterHandle() {
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if (in.getInt(in.readerIndex()) != Protocol.MAGIC_NUMBER) {
            ctx.channel().close();
            return null;
        }

        return super.decode(ctx, in);
    }
}