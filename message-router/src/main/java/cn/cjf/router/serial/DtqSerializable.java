package cn.cjf.router.serial;

import cn.cjf.router.config.AlgorithmConf;
import cn.cjf.router.config.ConstantConf;
import cn.cjf.router.protocal.Packet;
import cn.cjf.router.protocal.dtqprotocal.DtqRequest;
import cn.cjf.router.protocal.dtqprotocal.DtqResponse;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * 到路由的协议：
 * 魔法数字+版本号+路由算法标识+数据总长度+路由算法数据长度+路由算法byte数组+业务数据长度+业务数据byte数组
 * 路由到客户端的协议：
 * 魔法数字+版本号+业务数据长度+业务数据byte数组
 */
public class DtqSerializable implements Serializable {
    public static final int DEFAULT_ALGORITHM = AlgorithmConf.DATIQI_ALGORITHM;

    @Override
    public ByteBuf encode(Packet response) {
        DtqResponse dtqResponse = (DtqResponse) response;

        ByteBuf byteBuf = Unpooled.buffer(ConstantConf.INT_BYTE * 3 + dtqResponse.getBizLength());
        byteBuf.writeInt(dtqResponse.getBizLength());
        byteBuf.writeBytes(dtqResponse.getBizData());

        return byteBuf;
    }

    @Override
    public Packet decode(ByteBuf byteBuf) {
        int algorithm = byteBuf.getInt(ConstantConf.INT_BYTE);
        int totalLength = byteBuf.getInt(ConstantConf.INT_BYTE);
        int algorithmLength = byteBuf.getInt(ConstantConf.INT_BYTE);
        byte[] algorithmDataArr = new byte[algorithmLength];
        byteBuf.readBytes(algorithmDataArr);
        int bizLength = byteBuf.getInt(ConstantConf.INT_BYTE);
        byte[] bizDataArr = new byte[bizLength];
        byteBuf.readBytes(bizDataArr);

        return new DtqRequest(algorithm, totalLength,
                algorithmLength, algorithmDataArr,
                bizLength, bizDataArr);
    }
}
