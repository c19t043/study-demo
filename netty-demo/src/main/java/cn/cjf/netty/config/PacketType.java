package cn.cjf.chat.config;

/**
 * @author CJF
 */
public interface PacketType {
    Byte LOGIN_ECHO_PACKET = 1;
    Byte LOGIN_PACKET = 2;
    Byte MESSAGE_PACKET = 3;
    Byte HEART_BEAT_PACKET = 4;
}
