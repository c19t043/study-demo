package cn.cjf.util;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: lihang
 */
public class JPushUtil {
    private static final Logger LOG = LoggerFactory.getLogger(JPushUtil.class);

    private static String masterSecret;
    private static String appKey;

    /**
     * 推送消息至全平台全用户
     */
    public static PushResult pushMsgToAllPlatformAndAudience(String content) {
        PushResult result = null;
        ClientConfig clientConfig = ClientConfig.getInstance();
        final JPushClient jpushClient =
                new JPushClient(masterSecret,
                        appKey, null, clientConfig);

        try {
            PushPayload payload = PushPayload.newBuilder()
                    .setPlatform(Platform.all())
                    .setAudience(Audience.all())
                    .setNotification(Notification.alert(content))
                    .setMessage(Message.content(content))
                    .build();
            result = jpushClient.sendPush(payload);
            LOG.info("Got result - " + result);
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
        } finally {
            jpushClient.close();
        }
        return result;
    }
}
