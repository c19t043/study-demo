package cn.cjf.rocketmq.consumer;

import cn.cjf.rocketmq.MessageHandler;
import cn.cjf.util.DateUtils;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerOrderly;
import com.alibaba.rocketmq.common.MixAll;
import com.alibaba.rocketmq.common.message.MessageExt;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

public class RocketMQPushConsumer extends DefaultMQPushConsumer implements InitializingBean, DisposableBean {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private MessageHandler handler;

    public RocketMQPushConsumer() {
        super();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.hasLength(getNamesrvAddr(), "MQ服务器地址不能为空");

        if (StringUtils.isEmpty(getInstanceName()) || "DEFAULT".equals(getInstanceName())) {
            setInstanceName("CINS_" + DateUtils.getDateStr(new Date(), "yyyyMMddHHmmssSSS"));
        }

        Assert.notNull(handler, "Handler 不能为空");

        Assert.hasLength(handler.getTopic(), "Topic不能为空");

        Assert.hasLength(handler.getTag(), "Tag不能为空");

        if (StringUtils.isEmpty(getConsumerGroup()) || MixAll.DEFAULT_CONSUMER_GROUP.equals(getConsumerGroup())) {
            setConsumerGroup("CG_" + DateUtils.getDateStr(new Date(), "yyyyMMddHHmmssSSS"));
        }

        logger.info("启动MQ消费者，Group:{},Instance:{},Topic:{},Tag:{}", getConsumerGroup(), getInstanceName(),
                handler.getTopic(), handler.getTag());

        super.subscribe(handler.getTopic(), handler.getTag());

        super.registerMessageListener(new MessageListenerOrderly() {

            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                logger.info("接收MQ消息：Group:{},Instance:{}：{}", getConsumerGroup(), getInstanceName(), msgs);
                ConsumeOrderlyStatus result = ConsumeOrderlyStatus.SUCCESS;
                for (MessageExt messageExt : msgs) {

                    try {
                        result = handler.processMsg(messageExt.getMsgId(),
                                new String(messageExt.getBody(), MixAll.DEFAULT_CHARSET));
                    } catch (Exception e) {
                        logger.error("处理消息[{}]异常,", messageExt.getMsgId(), e);
                        result = ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                    }
                    if (ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT.equals(result)) {
                        break;
                    }
                }
                return result;
            }
        });

        super.start();

    }

    @Override
    public void destroy() throws Exception {
        logger.info("关闭MQ消费者，Group:{},Instance:{},Topic:{}", getConsumerGroup(), getInstanceName(), handler.getTopic());
        super.shutdown();
    }

    public void setHandler(MessageHandler handler) {
        this.handler = handler;
    }

}
