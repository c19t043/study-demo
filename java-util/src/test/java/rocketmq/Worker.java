package rocketmq;

import cn.cjf.rocketmq.producer.RocketMQProducer;

public class Worker implements Runnable {

	private int n = 1;

	// private Random random = new Random();

	private int no;
	private RocketMQProducer producer;

	public Worker(int no, RocketMQProducer producer, int total) {
		super();
		System.out.println("启动线程" + no + "...");
		this.no = no;
		this.producer = producer;
		this.n = total;
	}

	@Override
	public void run() {
		for (int j = 0; j < n; j++) {
			String msg = "BODY[" + no + "-" + j + "]";
			System.out.println("线程[" + no + "]发送消息   " + msg + ":" + producer.send("Tag", msg));
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
