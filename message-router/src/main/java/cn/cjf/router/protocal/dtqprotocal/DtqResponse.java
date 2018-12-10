package cn.cjf.router.protocal.dtqprotocal;

import cn.cjf.router.protocal.Packet;
import cn.cjf.router.serial.DtqSerializable;
import lombok.Data;

@Data
public class DtqResponse implements Packet {
    private int bizLength;
    private byte[] bizData;

    @Override
    public int getAlgorithm() {
        return DtqSerializable.DEFAULT_ALGORITHM;
    }
}
