package cn.cjf.chat.config;

/**
 * netty命令
 *
 * @author CJF
 * @date 2018-10-12
 */
public enum CommandEnum {
    /**
     * 单聊命令
     */
    SINGLE_CHAT(10),
    /**
     * 群聊命令
     */
    GROUP_CHAT(20),
    /**
     * 认证命令：登录
     */
    AUTH_LOGIN(100),
    /**
     * 服务端响应消息
     */
    SERVER_MESSAGE_RESPONSE(200),
    /**
     * 客户端发送消息
     */
    CLIENT_SEND_MESSAGE(300),
    /**
     * 单聊命令（一对一）：开始
     */
    SINGLE_CHAT_START(400),
    /**
     * 单聊命令：退出
     */
    SINGLE_CHAT_QUIT(500),
    /**
     * 群组命令：创建群组
     */
    GROUP_CHAT_CREATE(600),
    /**
     * 群组命令：展示客户端自己拥有的群组
     */
    GROUP_CHAT_DISPLAY_SELF(700),
    /**
     * 群组命令：将指定用户加入到指定群组中
     */
    GROUP_CHAT_PLEASE_JOIN(800),
    /**
     * 群组命令：用户申请加入指定群组
     */
    GROUP_CHAT_APPLY_JOIN(900),
    /**
     * 群聊命令（多对多）：开始
     */
    GROUP_CHAT_START(1000),
    /**
     * 群聊命令（多对多）：退出
     */
    GROUP_CHAT_QUIT(1100);

    private int code;

    CommandEnum(int code) {
        this.code = code;
    }

    public static void main(String[] args) {
        System.out.println(GROUP_CHAT_QUIT);
    }

    public int getCode() {
        return code;
    }
}
