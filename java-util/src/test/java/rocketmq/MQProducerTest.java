package rocketmq;

import cn.cjf.rocketmq.producer.RocketMQProducer;

import java.util.HashMap;
import java.util.Map;

public class MQProducerTest {

	public static void main(String[] args) throws Exception {
		RocketMQProducer producer = new RocketMQProducer();
		producer.setNamesrvAddr("127.0.0.1:9876");
		producer.setTopic("topic_1");

		producer.afterPropertiesSet();

		Map<String, Object> map = new HashMap<>();
		map.put("userId", 47);
		map.put("orderId", 203);
		map.put("courseIds", new long[] { 1038 });
		System.out.println("发送完毕：" + producer.send("tag_order_complete", map));

	}

}
