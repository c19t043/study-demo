package cn.cjf.chat.utils;

import cn.cjf.chat.config.Attributes;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

/**
 * @author CJF
 */
public class ChannelBindKeyUtil {

    /**
     * 绑定登录用户userId
     *
     * @param channel 通道
     * @param userId  用户id
     */
    public static void bindLoginUserId(Channel channel, String userId) {
        channel.attr(Attributes.USER_ID).set(userId);
    }

    /**
     * 获取登录用户userId
     *
     * @param channel 通道
     * @return String
     */
    public static String getLoginUserId(Channel channel) {
        return channel.attr(Attributes.USER_ID).get();
    }

    /**
     * 绑定登录用户key
     *
     * @param channel 通道
     */
    public static void bindLoginKey(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    /**
     * 查看用户是否登录userId
     *
     * @param channel 通道
     * @return boolean-->true:已登录;false:未登录
     */
    public static boolean hasLogin(Channel channel) {
        Boolean hasLogin = channel.attr(Attributes.LOGIN).get();
        return hasLogin != null && hasLogin.equals(true);
    }

    /**
     * 绑定命令
     *
     * @param channel 通道
     * @param command 命令
     */
    public static void bindKey(Channel channel, AttributeKey<String> command, String value) {
        channel.attr(command).set(value);
    }

    /**
     * 查看是否绑定命令
     *
     * @param channel 通道
     * @param command 命令
     */
    public static boolean hasBindKey(Channel channel, AttributeKey<String> command, String value) {
        String bindValue = channel.attr(command).get();
        return bindValue != null && bindValue.equals(value);
    }
}
