package cn.cjf.ok2.redis.lock.distribute.lock;

import java.util.concurrent.TimeUnit;

public interface DistributedLock {

    long DEFAULT_TIMEOUT = 0;

    long DEFAULT_EXPIRE_TIME = 1000;

    int DEFAULT_RETRY_TIMES = 0;

    TimeUnit DEFAULT_TIMEUNIT = TimeUnit.MILLISECONDS;

    boolean lock(String key);

    boolean lock(String key, long expire);

    boolean lock(String key, long expire, TimeUnit timeUnit);

    boolean lock(String key, long expire, int retryTimes, TimeUnit timeUnit);

    boolean tryLock(String key, long timeout);

    boolean tryLock(String key, long expire, long timeout);

    boolean tryLock(String key, long expire, long timeout, TimeUnit timeUnit);

    boolean tryLock(String key, long expire, int retryTimes, long timeout, TimeUnit timeUnit);

    boolean releaseLock(String key);
}
