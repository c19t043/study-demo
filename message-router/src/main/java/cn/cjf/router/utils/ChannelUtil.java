package cn.cjf.router.utils;

import java.util.UUID;

public class ChannelUtil {
    /**
     * 生成routeId
     */
    public static String generateRouteId() {
        return generateChannelId();
    }

    /**
     * 生成channelId
     */
    public static String generateChannelId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
