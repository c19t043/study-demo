package cn.cjf.chat.serializable;

import cn.cjf.chat.domain.Packet;

/**
 * @author CJF
 */
public interface SerializableBase {
    /**
     * 获取协议版本
     *
     * @return Byte
     */
    Byte getSerializableType();

    /**
     * 编码
     *
     * @param packet 数据包
     * @return byte[]
     */
    byte[] object2Bytes(Packet packet);

    /**
     * 解码
     *
     * @param datas byte数据包
     * @param clazz 反序列类型
     * @return Packet
     */
    Packet bytes2Object(byte[] datas, Class<? extends Packet> clazz);
}
