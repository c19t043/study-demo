package cn.cjf.rocketmq.consumer;

import cn.cjf.rocketmq.MessageProcessor;
import cn.cjf.util.DateUtils;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.*;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.MixAll;
import com.alibaba.rocketmq.common.message.MessageConst;
import com.alibaba.rocketmq.common.message.MessageExt;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/7/25.
 */
public class RocketMQPushConsumerV1 extends DefaultMQPushConsumer implements InitializingBean, DisposableBean {
    private Logger logger = LoggerFactory.getLogger(getClass());

    //消息处理器
    private MessageProcessor messageProcessor;

    //默认为并行消费
    private boolean concurrently = true;

    @Override
    public void destroy() throws Exception {
        super.shutdown();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.hasLength(getNamesrvAddr(), "MQ服务器地址不能为空");

        if (StringUtils.isEmpty(getInstanceName()) || "DEFAULT".equals(getInstanceName())) {
            setInstanceName("CINS_" + DateUtils.getDateStr(new Date(), "yyyyMMddHHmmssSSS"));
        }

        Assert.notNull(messageProcessor, "Handler 不能为空");

        Assert.hasLength(messageProcessor.getTopic(), "Topic不能为空");

        Assert.hasLength(messageProcessor.getTag(), "Tag不能为空");

        if (StringUtils.isEmpty(getConsumerGroup()) || MixAll.DEFAULT_CONSUMER_GROUP.equals(getConsumerGroup())) {
            setConsumerGroup("CG_" + DateUtils.getDateStr(new Date(), "yyyyMMddHHmmssSSS"));
        }

        logger.info("启动MQ消费者，Group:{},Instance:{},Topic:{},Tag:{}", getConsumerGroup(), getInstanceName(),
                messageProcessor.getTopic(), messageProcessor.getTag());

        super.subscribe(messageProcessor.getTopic(), messageProcessor.getTag());

        this.startSubscribe();

        super.start();
    }

    public void subscribe() throws MQClientException {
        Assert.hasLength(getNamesrvAddr(), "MQ服务器地址不能为空");

        if (StringUtils.isEmpty(getInstanceName()) || "DEFAULT".equals(getInstanceName())) {
            setInstanceName("CINS_" + DateUtils.getDateStr(new Date(), "yyyyMMddHHmmssSSS"));
        }

        Assert.notNull(messageProcessor, "Handler 不能为空");

        Assert.hasLength(messageProcessor.getTopic(), "Topic不能为空");

        Assert.hasLength(messageProcessor.getTag(), "Tag不能为空");

        if (StringUtils.isEmpty(getConsumerGroup()) || MixAll.DEFAULT_CONSUMER_GROUP.equals(getConsumerGroup())) {
            setConsumerGroup("CG_" + DateUtils.getDateStr(new Date(), "yyyyMMddHHmmssSSS"));
            throw new MQClientException(-1, "Please set consume group");
        }

        logger.info("启动MQ消费者，Group:{},Instance:{},Topic:{},Tag:{}", getConsumerGroup(), getInstanceName(),
                messageProcessor.getTopic(), messageProcessor.getTag());

        super.subscribe(messageProcessor.getTopic(), messageProcessor.getTag());

        this.startSubscribe();
    }

    private void startSubscribe() {
        if(concurrently) {
            super.registerMessageListener(new MessageListenerConcurrently() {

                /**
                 * It is not recommend to throw exception,rather than returning ConsumeConcurrentlyStatus.RECONSUME_LATER if consumption failure
                 *
                 * @param msgs    msgs.size() >= 1<br>
                 *                DefaultMQPushConsumer.consumeMessageBatchMaxSize=1，you can modify here
                 * @param context
                 * @return
                 */
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    logger.info("接收MQ消息：Group:{},Instance:{}：{}", getConsumerGroup(), getInstanceName(), msgs);
                    ConsumeConcurrentlyStatus result = ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    for (MessageExt messageExt : msgs) {

                        try {
                            String msgId = messageExt.getMsgId();
                            if(messageExt.getReconsumeTimes() > 0) {
                                msgId = messageExt.getProperties().get(MessageConst.PROPERTY_ORIGIN_MESSAGE_ID);
                                logger.info("收到重试消息：new message id:{}, origin message id:{}, message:{}", messageExt.getMsgId(),
                                                msgId, messageExt);
                            }
                            result = messageProcessor.processMsgConcurrently(msgId,
                                    new String(messageExt.getBody(), MixAll.DEFAULT_CHARSET));
                        } catch (Exception e) {
                            logger.error("处理消息[{}]异常,", messageExt.getMsgId(), e);
                            result = ConsumeConcurrentlyStatus.RECONSUME_LATER;
                        }
                        if (ConsumeConcurrentlyStatus.RECONSUME_LATER.equals(result)) {
                            break;
                        }
                    }
                    return result;
                }
            });
        } else {
            super.registerMessageListener(new MessageListenerOrderly() {

                @Override
                public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                    logger.info("接收MQ消息：Group:{},Instance:{}：{}", getConsumerGroup(), getInstanceName(), msgs);
                    ConsumeOrderlyStatus result = ConsumeOrderlyStatus.SUCCESS;
                    for (MessageExt messageExt : msgs) {

                        try {
                            String msgId = messageExt.getMsgId();
                            if(messageExt.getReconsumeTimes() > 0) {
                                msgId = messageExt.getProperties().get(MessageConst.PROPERTY_ORIGIN_MESSAGE_ID);
                                logger.info("收到重试消息：new message id:{}, origin message id:{}, message:{}", messageExt.getMsgId(),
                                        msgId, messageExt);
                            }
                            result = messageProcessor.processMsgOrderly(msgId,
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
        }
    }

    public void setMessageProcessor(MessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
    }

    public void setConcurrently(boolean concurrently) {
        this.concurrently = concurrently;
    }
}
