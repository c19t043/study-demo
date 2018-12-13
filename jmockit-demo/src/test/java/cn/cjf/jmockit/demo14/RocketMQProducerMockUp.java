package cn.cjf.jmockit.demo14;

import mockit.Mock;
import mockit.MockUp;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//MQ消息发送者 的MockUp(伪类）
public class RocketMQProducerMockUp extends MockUp<DefaultMQProducer> {
 
    @Mock
    void init() throws MQClientException {
        // 构造函数也什么都不做
    }
 
    @Mock
    void start() throws MQClientException {
        // 启动，什么都不做 
    }
 
    @Mock
    void shutdown() {
        // 关闭，也什么都不做 
    }
 
    @Mock
    List<MessageQueue> fetchPublishMessageQueues(final String topic) throws MQClientException {
        // 欺骗调用方，返回不存在的消息队列，因为消息并不会真正发送嘛
        List<MessageQueue> queues = new ArrayList<MessageQueue>();
        MessageQueue q = new MessageQueue();
        q.setBrokerName("testbrokername");
        q.setQueueId(1);
        q.setTopic("testtopic");
        queues.add(q);
        return queues;
    }
 
    // 下面是对各个send方法的mock,都返回消息成功结果
    @Mock
    SendResult send(final Message msg)
            throws MQClientException, RemotingException, MQBrokerException, InterruptedException {
        return newSuccessSendResult();
    }
 
    @Mock
    SendResult send(final Message msg, final long timeout)
            throws MQClientException, RemotingException, MQBrokerException, InterruptedException {
        return newSuccessSendResult();
    }
 
    @Mock
    void send(final Message msg, final SendCallback sendCallback)
            throws MQClientException, RemotingException, InterruptedException {
        sendCallback.onSuccess(this.newSuccessSendResult());
    }
 
    @Mock
    void send(final Message msg, final SendCallback sendCallback, final long timeout)
            throws MQClientException, RemotingException, InterruptedException {
        sendCallback.onSuccess(this.newSuccessSendResult());
    }
 
    @Mock
    void sendOneway(final Message msg) throws MQClientException, RemotingException, InterruptedException {
 
    }
 
    @Mock
    SendResult send(final Message msg, final MessageQueue mq)
            throws MQClientException, RemotingException, MQBrokerException, InterruptedException {
        return newSuccessSendResult();
    }
 
    @Mock
    SendResult send(final Message msg, final MessageQueue mq, final long timeout)
            throws MQClientException, RemotingException, MQBrokerException, InterruptedException {
        return newSuccessSendResult();
    }
 
    @Mock
    void send(final Message msg, final MessageQueue mq, final SendCallback sendCallback)
            throws MQClientException, RemotingException, InterruptedException {
        sendCallback.onSuccess(this.newSuccessSendResult());
    }
 
    @Mock
    void send(final Message msg, final MessageQueue mq, final SendCallback sendCallback, long timeout)
            throws MQClientException, RemotingException, InterruptedException {
        sendCallback.onSuccess(this.newSuccessSendResult());
    }
 
    @Mock
    void sendOneway(final Message msg, final MessageQueue mq)
            throws MQClientException, RemotingException, InterruptedException {
 
    }
 
    @Mock
    SendResult send(final Message msg, final MessageQueueSelector selector, final Object arg)
            throws MQClientException, RemotingException, MQBrokerException, InterruptedException {
        return newSuccessSendResult();
    }
 
    @Mock
    SendResult send(final Message msg, final MessageQueueSelector selector, final Object arg, final long timeout)
            throws MQClientException, RemotingException, MQBrokerException, InterruptedException {
        return newSuccessSendResult();
    }
 
    @Mock
    void send(final Message msg, final MessageQueueSelector selector, final Object arg, final SendCallback sendCallback)
            throws MQClientException, RemotingException, InterruptedException {
        sendCallback.onSuccess(this.newSuccessSendResult());
    }
 
    @Mock
    void send(final Message msg, final MessageQueueSelector selector, final Object arg, final SendCallback sendCallback,
            final long timeout) throws MQClientException, RemotingException, InterruptedException {
        sendCallback.onSuccess(this.newSuccessSendResult());
    }
 
    @Mock
    void sendOneway(final Message msg, final MessageQueueSelector selector, final Object arg)
            throws MQClientException, RemotingException, InterruptedException {
 
    }
 
    @Mock
    TransactionSendResult sendMessageInTransaction(final Message msg, final LocalTransactionExecuter tranExecuter,
            final Object arg) throws MQClientException {
        return newTransactionSendResult();
    }
 
    private TransactionSendResult newTransactionSendResult() {
        TransactionSendResult success = new TransactionSendResult();
        success.setSendStatus(SendStatus.SEND_OK);
        success.setMsgId(UUID.randomUUID().toString());
        MessageQueue q = new MessageQueue();
        q.setBrokerName("testbrokername");
        q.setQueueId(1);
        q.setTopic("testtopic");
        success.setMessageQueue(q);
        success.setLocalTransactionState(LocalTransactionState.COMMIT_MESSAGE);
        return success;
    }
 
    private SendResult newSuccessSendResult() {
        SendResult success = new SendResult();
        success.setSendStatus(SendStatus.SEND_OK);
        success.setMsgId(UUID.randomUUID().toString());
        MessageQueue q = new MessageQueue();
        q.setBrokerName("testbrokername");
        q.setQueueId(1);
        q.setTopic("testtopic");
        success.setMessageQueue(q);
        return success;
    }
}