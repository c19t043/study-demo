package rocketmq;


import cn.cjf.rocketmq.consumer.RocketMQPushConsumer;

public class MQConsumerTest {

	public static void main(String[] args) throws Exception {

		RocketMQPushConsumer consumer1 = new RocketMQPushConsumer();
		consumer1.setNamesrvAddr("192.168.57.101:9876");
		consumer1.setInstanceName("instance_1");
		consumer1.setConsumerGroup("TestGroup8");
		HandlerImpl handler1 = new HandlerImpl();
		handler1.setTopic("topic_message");
		handler1.setTag("tag_message");
		consumer1.setHandler(handler1);
		consumer1.afterPropertiesSet();


	}

}
