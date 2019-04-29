package cn.cjf.rocketmq;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;

/**
 * Created by Administrator on 2016/7/25.
 */
public interface MessageProcessor {

    /**
     * 获取消息订阅主题
     * @return
     */
    String getTopic();

    /**
     * 获取消息tag
     * @return
     */
    String getTag();
    /**
     * 消息处理
     *
     * @param msgJson
     *            消息JSON
     */
    ConsumeConcurrentlyStatus processMsgConcurrently(String msgId, String msgJson);

    /**
     * 消息处理
     *
     * @param msgJson
     *            消息JSON
     */
    ConsumeOrderlyStatus processMsgOrderly(String msgId, String msgJson);
}
