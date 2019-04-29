package cn.cjf.rocketmq;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.GenericTypeResolver;

/**
 * Created by Administrator on 2016/7/25.
 */
public abstract class MessageProcessorAbstract<T> implements MessageProcessor {
	protected static final Logger logger = LoggerFactory.getLogger(MessageProcessorAbstract.class);
	private String topic;
	private String tag;

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * 消息所属的Topic
	 *
	 * @return
	 */
	public String getTopic() {
		return topic;
	}

	/**
	 * 消息所属的Tag
	 *
	 * @return
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * 消息处理
	 *
	 * @param msgId
	 * @param msgJson
	 */
	@Override
	public ConsumeConcurrentlyStatus processMsgConcurrently(String msgId, String msgJson) {
		T msgObject = decode(msgJson);
		return processConcurrently(msgId, msgObject);
	}

	/**
	 * 消息处理
	 *
	 * @param msgJson
	 *            消息JSON
	 */
	@Override
	public ConsumeOrderlyStatus processMsgOrderly(String msgId, String msgJson) {
		T msgObject = decode(msgJson);

		return processOrderly(msgId, msgObject);
	}

	protected T decode(String msgJson) {
		Class<T> tClass = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(),
				MessageProcessorAbstract.class);
		return JSON.parseObject(msgJson, tClass);
	}

	public abstract ConsumeConcurrentlyStatus processConcurrently(String msgId, T object);

	public abstract ConsumeOrderlyStatus processOrderly(String msgId, T object);
}
