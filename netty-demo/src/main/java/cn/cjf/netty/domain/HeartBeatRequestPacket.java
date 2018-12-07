package cn.cjf.netty.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static cn.cjf.netty.config.PacketType.HEART_BEAT_PACKET;

@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
/**
 * @author CJF
 */
public class HeartBeatRequestPacket extends Packet {
    private String messageId;

    @Override
    public Byte getType() {
        return HEART_BEAT_PACKET;
    }

    @Override
    public String getMessageId() {
        return messageId;
    }

    @Override
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
