package cn.cjf.netty.domain;

import cn.cjf.netty.config.PacketType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @author CJF
 */
@Data
@AllArgsConstructor
@ToString
public class MessagePacket extends Packet {
    private String messageId;
    private String message;
    private int messageSrcType = 0;

    @Override
    public Byte getType() {
        return PacketType.MESSAGE_PACKET;
    }

    public MessagePacket(String message, int messageSrcType) {
        this.message = message;
        this.messageSrcType = messageSrcType;
    }

    public MessagePacket(String message) {
        this.message = message;
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
