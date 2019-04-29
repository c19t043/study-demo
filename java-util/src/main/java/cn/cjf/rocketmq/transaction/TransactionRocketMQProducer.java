package cn.cjf.rocketmq.transaction;

import cn.cjf.rocketmq.producer.RocketMQProducer;
import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.LocalTransactionExecuter;
import com.alibaba.rocketmq.client.producer.LocalTransactionState;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.TransactionCheckListener;
import com.alibaba.rocketmq.common.MixAll;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;

import java.io.UnsupportedEncodingException;

/**
 * 可发送事务消息MQ生产者
 */
public class TransactionRocketMQProducer extends RocketMQProducer {

    public <T> String send(T msg, LocalTransactionInvoker invoker, Object callbackArg, BusinessListener listener) {
        String body = JSON.toJSONString(msg);
        logger.info("发送MQ消息：{}", body);

        Message message = null;
        try {
            message = new Message(getTopic(), getTag(), body.getBytes(MixAll.DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.error("unsupported charset, {}", e);
            return null;
        }
        SendResult sendResult = this.sendMessageInTransaction(message, invoker, callbackArg, listener);
        if (sendResult != null) {
            return sendResult.getMsgId();
        }

        return null;
    }

    private SendResult sendMessageInTransaction(Message message,
                                                LocalTransactionInvoker invoker,
                                                Object callbackArg,
                                                BusinessListener listener) {
        LocalTransactionExecuter localTransactionExecuter = new LocalTransactionExecuter() {
            @Override
            public LocalTransactionState executeLocalTransactionBranch(Message msg, Object arg) {
                cn.cjf.rocketmq.transaction.LocalTransactionState state = invoker.execute(
                        new MessageWrapper(msg), arg
                );

                LocalTransactionState localTransactionState = null;
                switch (state) {
                    case OK:
                        localTransactionState = LocalTransactionState.COMMIT_MESSAGE;
                        break;
                    case FAILED:
                        localTransactionState = LocalTransactionState.ROLLBACK_MESSAGE;
                        break;
                    default:
                        localTransactionState = LocalTransactionState.UNKNOW;
                        break;
                }

                return localTransactionState;
            }
        };

        setTransactionCheckListener(new TransactionCheckListener() {
            @Override
            public LocalTransactionState checkLocalTransactionState(MessageExt msg) {
                MessageWrapper messageWrapper = new MessageWrapper(msg);
                return listener.checkState(messageWrapper);
            }
        });

        try {
            return super.sendMessageInTransaction(message, localTransactionExecuter, callbackArg);
        } catch (MQClientException e) {
            logger.error("send transaction message failed, {}", e);
        }

        return null;
    }
}
