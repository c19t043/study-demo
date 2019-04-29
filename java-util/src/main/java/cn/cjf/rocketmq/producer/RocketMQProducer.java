package cn.cjf.rocketmq.producer;

import cn.cjf.rocketmq.MessageSender;
import cn.cjf.util.DateUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.TransactionMQProducer;
import com.alibaba.rocketmq.common.MixAll;
import com.alibaba.rocketmq.common.message.Message;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.Date;

public class RocketMQProducer extends TransactionMQProducer implements MessageSender, InitializingBean, DisposableBean {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private String topic;
    private String tag;

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.hasLength(getNamesrvAddr(), "MQ服务器地址不能为空");

        if (StringUtils.isEmpty(getInstanceName()) || "DEFAULT".equals(getInstanceName())) {
            setInstanceName("PINS_" + DateUtils.getDateStr(new Date(), "yyyyMMddHHmmssSSS"));
        }
        Assert.hasLength(topic, "Topic不能为空");

        if (StringUtils.isEmpty(getProducerGroup()) || MixAll.DEFAULT_PRODUCER_GROUP.equals(getProducerGroup())) {
            setProducerGroup("PG_" + DateUtils.getDateStr(new Date(), "yyyyMMddHHmmssSSS"));
        }

        logger.info("启动MQ生产者，Group:{},Instance:{},Topic:{}", getProducerGroup(), getInstanceName(), topic);

        super.start();
    }

    @Override
    public void destroy() throws Exception {
        logger.info("关闭MQ生产者，Group:{},Instance:{},Topic:{}", getProducerGroup(), getInstanceName(), topic);
        super.shutdown();
    }

    @Override
    public <T> String send(T msg) {
        return doSend(tag, msg, 0);
    }

    @Override
    public <T> String send(T msg, int messageDelayLevel) {
        return doSend(tag, msg, messageDelayLevel);
    }

    @Override
    public <T> String send(String tag, T msg) {
        return doSend(tag, msg, 0);
    }

    @Override
    public <T> String send(String tag, T msg, int messageDelayLevel) {
        return doSend(tag, msg, messageDelayLevel);
    }

    private <T> String doSend(String tag, T msg, int messageDelayLevel) {
        String msgId = null;
        try {
            String body = JSON.toJSONString(msg);
            Message message = new Message(topic, tag, body.getBytes(MixAll.DEFAULT_CHARSET));
            if (messageDelayLevel > 0) {
                message.setDelayTimeLevel(messageDelayLevel);
            }
            SendResult sendResult = super.send(message);
            msgId = sendResult.getMsgId();
            logger.info("发送MQ消息：Topic[{}],Tag[{}],Msg[{}], msgId:{}", topic, tag, msg, msgId);
        } catch (Exception e) {
            logger.error("发送MQ消息异常：{}", e);
        }

        return msgId;
    }

}
