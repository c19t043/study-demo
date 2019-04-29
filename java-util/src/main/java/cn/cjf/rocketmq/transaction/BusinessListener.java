package cn.cjf.rocketmq.transaction;

/**
 * Created by error on 2017/12/26.
 */
public interface BusinessListener {

    com.alibaba.rocketmq.client.producer.LocalTransactionState checkState(MessageWrapper msg);
}
