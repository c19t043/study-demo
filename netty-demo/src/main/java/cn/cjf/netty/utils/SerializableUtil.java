package cn.cjf.chat.utils;

import cn.cjf.chat.config.PacketType;
import cn.cjf.chat.config.Protocol;
import cn.cjf.chat.config.SerializableType;
import cn.cjf.chat.domain.LoginEchoPacket;
import cn.cjf.chat.domain.LoginPacket;
import cn.cjf.chat.domain.MessagePacket;
import cn.cjf.chat.domain.Packet;
import cn.cjf.chat.serializable.FastJsonSerializable;
import cn.cjf.chat.serializable.SerializableBase;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

/**
 * @author CJF
 */
public class SerializableUtil {

    private static Map<Byte, Class<? extends Packet>> clazzMap = new HashMap<>();
    private static Map<Byte, SerializableBase> serializableMap = new HashMap<>();

    static {
        clazzMap.put(PacketType.LOGIN_ECHO_PACKET, LoginEchoPacket.class);
        clazzMap.put(PacketType.LOGIN_PACKET, LoginPacket.class);
        clazzMap.put(PacketType.MESSAGE_PACKET, MessagePacket.class);

        serializableMap.put(SerializableType.FAST_JSON, new FastJsonSerializable());
    }

    /**
     * 编码
     *
     * @param byteBuf 数据包缓存
     * @param packet  数据包
     */
    public static void encode(ByteBuf byteBuf, Packet packet) {
        byteBuf.writeInt(Protocol.MAGIC_NUMBER);
        byteBuf.writeByte(Protocol.PROTOCAL_VERSION);

        byteBuf.writeByte(SerializableType.FAST_JSON);

        byteBuf.writeByte(packet.getType());

        SerializableBase serializableBase = serializableMap.get(SerializableType.FAST_JSON);
        byte[] bytes = serializableBase.object2Bytes(packet);

        byteBuf.writeInt(bytes.length);

        byteBuf.writeBytes(bytes);
    }

    /**
     * 解码
     *
     * @param byteBuf 数据包缓存
     */
    public static Packet decode(ByteBuf byteBuf) {
        // 跳过 magic number
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 序列化算法
        byte serializeAlgorithm = byteBuf.readByte();

        //数据包类型
        byte dataType = byteBuf.readByte();

        //数据包长度
        int dataLength = byteBuf.readInt();

        byte[] bytes = new byte[dataLength];
        byteBuf.readBytes(bytes);

        return serializableMap.get(serializeAlgorithm).bytes2Object(bytes, clazzMap.get(dataType));
    }
}
