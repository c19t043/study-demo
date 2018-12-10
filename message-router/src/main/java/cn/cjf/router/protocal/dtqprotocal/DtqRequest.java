package cn.cjf.router.protocal.dtqprotocal;

import cn.cjf.router.protocal.Packet;
import cn.cjf.router.serial.DtqSerializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtqRequest implements Packet {
    private int algorithm;
    private int totalLength;
    private int algorithmLength;
    private byte[] algorithmData;
    private int bizLength;
    private byte[] bizData;

    public DtqRequest(int algorithm, byte[] algorithmData, byte[] bizData) {
        this.algorithm = algorithm;
        this.algorithmData = algorithmData;
        this.bizData = bizData;
    }

    @Override
    public int getAlgorithm() {
        return DtqSerializable.DEFAULT_ALGORITHM;
    }
}
