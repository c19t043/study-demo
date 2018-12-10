package cn.cjf.router.manager;

import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChannelManager {
    @Getter
    @Setter
    private String routerId;
    private static Map<String, Channel> channelMap = new ConcurrentHashMap<>();

    /**
     * 保存通道映射
     */
    public static void putChannel2Map(String key, Channel channel) {
        channelMap.put(key, channel);
    }

    /**
     * 获取通道映射
     */
    public static Channel getChannel2Map(String key) {
        return channelMap.get(key);
    }
}
