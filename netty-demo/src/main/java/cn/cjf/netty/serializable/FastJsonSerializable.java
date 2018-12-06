package cn.cjf.chat.serializable;

import cn.cjf.chat.config.SerializableType;
import cn.cjf.chat.domain.Packet;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author CJF
 */
public class FastJsonSerializable implements SerializableBase {
    private final static SerializerFeature[] features = new SerializerFeature[]{
            SerializerFeature.WriteClassName,
            //SerializerFeature.SkipTransientField,
            //SerializerFeature.DisableCircularReferenceDetect
    };

    @Override
    public Byte getSerializableType() {
        return SerializableType.FAST_JSON;
    }

    @Override
    public byte[] object2Bytes(Packet packet) {

        return JSON.toJSONBytes(packet);
    }

    @Override
    public Packet bytes2Object(byte[] datas, Class<? extends Packet> clazz) {
        return JSON.parseObject(datas, clazz);
    }

}
