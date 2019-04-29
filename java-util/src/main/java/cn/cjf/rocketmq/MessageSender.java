package cn.cjf.rocketmq;

/**
 * 消息生产者
 *
 * @author Lee
 *
 */
public interface MessageSender {

	/**
	 * 发送默认指定业务Tag消息
	 *
	 * @param msg
	 *            消息内容
	 * @return 返回消息ID，为空时表示消息发送失败
	 */
	public <T> String send(T msg);

	/**
	 * 发送默认指定业务Tag消息
	 *
	 * @param msg
	 *            消息内容
	 * @param level延时等级
	 * @return 返回消息ID，为空时表示消息发送失败
	 */
	public <T> String send(T msg, int level);

	/**
	 * 发送即时消息
	 *
	 * @param tag
	 *            消息所属的业务Tag
	 * @param msg
	 *            消息内容
	 * @return 返回消息ID，为空时表示消息发送失败
	 */
	public <T> String send(String tag, T msg);

	/**
	 * 发送延时消息
	 *
	 * @param tag
	 *            消息所属的业务Tag
	 * @param msg
	 *            消息内容
	 * @param level延时等级
	 * @return 返回消息ID，为空时表示消息发送失败
	 */
	public <T> String send(String tag, T msg, int level);

}
