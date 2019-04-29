package rocketmq;

import cn.cjf.rocketmq.MessageHandler;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;

public class HandlerImpl extends MessageHandler {

	@Override
	public ConsumeOrderlyStatus processMsg(String msgId, String msgJson) {
		System.out.println(getTopic() + "-" + getTag() + " 处理消息:" + msgId + msgJson);
		return ConsumeOrderlyStatus.SUCCESS;
	}

}
