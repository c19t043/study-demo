package cn.cjf.chat.domain;

import cn.cjf.chat.config.PacketType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
/**
 * @author CJF
 */
public class LoginEchoPacket extends Packet {
    private String message;
    private String messageId;

    public LoginEchoPacket(String message) {
        this.message = message;
    }

    @Override
    public Byte getType() {
        return PacketType.LOGIN_ECHO_PACKET;
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
