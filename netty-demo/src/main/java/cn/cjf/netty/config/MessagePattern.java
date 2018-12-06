package cn.cjf.chat.config;

public interface MessagePattern {
    /**
     * 解析客户端发送到服务端的消息
     * 1、userId，开头，为单聊发送给指定客户端
     * 2、groupId，开头，为群聊消息，发送给群聊用户
     * 3、!@userId，开头，为单聊回复消息
     * 4、!@groupId，开头，为群聊回复消息
     */
    String SINGLE_CHAT_PRE = "userId";
    String SINGLE_CHAT_RESPONSE_PRE = "!@userId";
    String GROUP_CHAT_PRE = "groupId";
    String GROUP_CHAT_RESPONSE_PRE = "!@groupId";
    /**
     * 消息分隔符:
     */
    String MESSAGE_SPLIT = ":";
    /**
     * 请求消息前缀
     */
    String REQUEST_MESSAGE_PRE = "@";

    /**
     * 单聊消息
     */
    int MESSAGE_SRC_TYPE_SINGLE = 1;
    /**
     * 群聊消息
     */
    int MESSAGE_SRC_TYPE_GROUP = 2;
}
