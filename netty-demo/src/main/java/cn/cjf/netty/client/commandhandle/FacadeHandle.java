package cn.cjf.chat.client.commandhandle;

import cn.cjf.chat.config.CommandEnum;
import cn.cjf.chat.config.ConstantConfig;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author CJF
 */
public class FacadeHandle {
    private static Map<String, BaseHandle> handleMap = new HashMap<>(ConstantConfig.MAP_INITIAL_CAPACITY);

    static {
        handleMap.put(CommandEnum.AUTH_LOGIN.toString(), new LoginCommandHandle());
        handleMap.put(CommandEnum.CLIENT_SEND_MESSAGE.toString(), new SendMessageCommandHandle());
        handleMap.put(CommandEnum.SINGLE_CHAT_START.toString(), new SingleChatStartCommandHandle());
        handleMap.put(CommandEnum.GROUP_CHAT_START.toString(), new GroupChatStartCommandHandle());
        handleMap.put(CommandEnum.GROUP_CHAT_CREATE.toString(), new GroupChatCreateCommandHandle());
        handleMap.put(CommandEnum.GROUP_CHAT_PLEASE_JOIN.toString(), new GroupChatPleaseJoinCommandHandle());
        handleMap.put(CommandEnum.GROUP_CHAT_APPLY_JOIN.toString(), new GroupChatApplyJoinCommandHandle());
        handleMap.put(CommandEnum.GROUP_CHAT_DISPLAY_SELF.toString(), new GroupChatApplyJoinCommandHandle());
    }

    public static void handle(Channel channel, Integer command, String o) {
        handleMap.get(command).execute(channel, o);
    }

    public static void handle(Channel channel, String command, String o) {
        if (!handleMap.containsKey(command)) {
            System.out.println("未能找到对应命令--->");
        } else {
            handleMap.get(command).execute(channel, o);
        }
    }
}
