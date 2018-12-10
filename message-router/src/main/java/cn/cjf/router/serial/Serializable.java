package cn.cjf.router.serial;

import cn.cjf.router.protocal.Packet;
import io.netty.buffer.ByteBuf;

public interface Serializable {
    ByteBuf encode(Packet packet);

    Packet decode(ByteBuf byteBuf);
}
