package cn.cjf.router.utils;

import cn.cjf.router.protocal.Packet;
import cn.cjf.router.serial.DtqSerializable;
import cn.cjf.router.serial.Serializable;
import io.netty.buffer.ByteBuf;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class SerializableUtil {

    private static Map<Integer, Serializable> serializableMap = new ConcurrentHashMap<>();

    static {
        serializableMap.put(DtqSerializable.DEFAULT_ALGORITHM, new DtqSerializable());
    }

    public static ByteBuf encode(Packet packet, int algorithm) {
        return serializableMap.get(algorithm).encode(packet);
    }

    public static Packet decode(ByteBuf byteBuf, int algorithm) {
        return serializableMap.get(algorithm).decode(byteBuf);
    }
}
