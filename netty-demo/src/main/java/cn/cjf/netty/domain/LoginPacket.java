package cn.cjf.chat.domain;

import cn.cjf.chat.config.PacketType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @author CJF
 */
@Data
@AllArgsConstructor
@ToString
public class LoginPacket extends Packet {
    private String userId;
    private String userName;
    private String messageId;

    public LoginPacket(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    @Override
    public Byte getType() {
        return PacketType.LOGIN_PACKET;
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
