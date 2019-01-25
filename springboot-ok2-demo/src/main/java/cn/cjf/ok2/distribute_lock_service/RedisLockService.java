package cn.cjf.ok2.distribute_lock_service;

/**
 * @Description: Redis分布式锁接口
 * @Author: fxb
 * @CreateDate: 2018/3/29 15:43
 * @Version: 1.0
 */
public interface RedisLockService {
    /**
     * 加锁成功，返回加锁时间
     * @param lockKey
     * @param threadName
     * @return
     */
    public Long  lock(String lockKey);

    /**
     * 解锁， 需要更新加锁时间，判断是否有权限
     * @param lockKey
     * @param lockValue
     * @param threadName
     */
    public void unlock(String lockKey, long lockValue);

    /**
     * 多服务器集群，使用下面的方法，代替System.currentTimeMillis()，获取redis时间，避免多服务的时间不一致问题！！！
     * @return
     */
    public long currtTimeForRedis();
}