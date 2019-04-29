package cn.cjf.rocketmq.transaction;

/**
 * Created by error on 2017/4/19.
 */
public interface LocalTransactionInvoker {
    /**
     * 执行本地事务
     * @param messageWrapper 消息
     * @param arg 回调参数
     * @return 本地事务执行状态
     */
    LocalTransactionState execute(final MessageWrapper messageWrapper, final Object arg);
}
