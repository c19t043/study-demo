package cn.cjf.rocketmq;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消息处理接口
 *
 * @author Lee
 *
 */
@Deprecated
public abstract class MessageHandler {

	protected Logger logger = LoggerFactory.getLogger(getClass());

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
	 * @param msgJson
	 *            消息JSON
	 */
	public abstract ConsumeOrderlyStatus processMsg(String msgId, String msgJson);

}
