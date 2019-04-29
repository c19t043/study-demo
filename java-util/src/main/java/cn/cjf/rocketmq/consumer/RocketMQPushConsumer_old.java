package cn.cjf.rocketmq.consumer;

import cn.cjf.rocketmq.MessageHandler;
import cn.cjf.util.DateUtils;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerOrderly;
import com.alibaba.rocketmq.common.MixAll;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

public class RocketMQPushConsumer_old implements InitializingBean, DisposableBean {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private String serverAddr;// 服务器地址
	private String consumerGroup = "CG_" + DateUtils.getDateStr(new Date());// 生成者分组，未添加为默认组
	private String instanceName;// 实例名称
	private MessageModel model = MessageModel.CLUSTERING;

	private DefaultMQPushConsumer consumer;

	private MessageHandler handler;

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.hasLength(serverAddr, "MQ服务器地址不能为空");

		Assert.hasLength(instanceName, "实例名称不能为空");

		Assert.notNull(handler, "Handler 不能为空");

		Assert.hasLength(handler.getTopic(), "Topic不能为空");

		Assert.hasLength(handler.getTag(), "Tag不能为空");

		logger.info("启动MQ消费者，Group:{},Instance:{},Topic:{},Tag:{}", consumerGroup, instanceName, handler.getTopic(),
				handler.getTag());

		consumer = new DefaultMQPushConsumer(consumerGroup);
		consumer.setNamesrvAddr(serverAddr);
		consumer.setInstanceName(instanceName);
		consumer.setConsumeThreadMax(1);
		consumer.setConsumeThreadMin(1);
		consumer.setMessageModel(model);

		consumer.subscribe(handler.getTopic(), handler.getTag());

		consumer.registerMessageListener(new MessageListenerOrderly() {

			@Override
			public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
				logger.info("接收MQ消息：Group:{},Instance:{}：{}", consumerGroup, instanceName, msgs);
				for (MessageExt messageExt : msgs) {
					try {
						handler.processMsg(messageExt.getMsgId(),
								new String(messageExt.getBody(), MixAll.DEFAULT_CHARSET));
					} catch (Exception e) {
						logger.error("处理消息[{}]异常,", messageExt.getMsgId(), e);
					}
				}
				return ConsumeOrderlyStatus.SUCCESS;
			}
		});

		consumer.start();

	}

	@Override
	public void destroy() throws Exception {
		logger.info("关闭MQ消费者，Group:{},Instance:{},Topic:{}", consumerGroup, instanceName, handler.getTopic());
		if (consumer != null) {
			consumer.shutdown();
		}
	}

	public void setHandler(MessageHandler handler) {
		this.handler = handler;
	}

	public void setConsumerGroup(String consumerGroup) {
		this.consumerGroup = consumerGroup;
	}

	public void setServerAddr(String serverAddr) {
		this.serverAddr = serverAddr;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}
}
