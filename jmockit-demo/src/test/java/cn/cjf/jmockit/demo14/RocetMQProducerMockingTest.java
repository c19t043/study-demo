package cn.cjf.jmockit.demo14;//RocketMQ消息生产者 Mock

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.util.Assert;

public class RocetMQProducerMockingTest {
    // 把RocketMQ的生产者mock
    @BeforeClass
    public static void mockRocketMQ() {
        new RocketMQProducerMockUp();
    }

    @Test
    public void testSendRocketMQMessage() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("test_producer");
        producer.setNamesrvAddr("192.168.0.2:9876;192.168.0.3:9876");
        producer.start();
        for (int i = 0; i < 20; i++) {
            Message msg = new Message("testtopic", "TagA", ("Hello " + i).getBytes());
            // 因为mq生产者已经mock,所以消息并不会真正的发送，即使nameServer连不上，也不影响单元测试的运行
            SendResult result = producer.send(msg);
            Assert.isTrue(result.getSendStatus() == SendStatus.SEND_OK);
            Assert.isTrue(result.getMsgId() != null);
        }
        producer.shutdown();
    }
}