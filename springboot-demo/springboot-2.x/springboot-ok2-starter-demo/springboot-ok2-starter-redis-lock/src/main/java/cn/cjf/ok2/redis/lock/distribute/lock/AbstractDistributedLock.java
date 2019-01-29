package cn.cjf.ok2.redis.lock.distribute.lock;

import java.util.concurrent.TimeUnit;

public abstract class AbstractDistributedLock implements DistributedLock {

    @Override
    public boolean lock(String key) {
        return tryLock(key, DEFAULT_EXPIRE_TIME, DEFAULT_RETRY_TIMES, DEFAULT_TIMEOUT, DEFAULT_TIMEUNIT);
    }

    @Override
    public boolean lock(String key, long expire) {
        return tryLock(key, expire, DEFAULT_RETRY_TIMES, DEFAULT_TIMEOUT, DEFAULT_TIMEUNIT);
    }

    @Override
    public boolean lock(String key, long expire, TimeUnit timeUnit) {
        return tryLock(key, expire, DEFAULT_RETRY_TIMES, DEFAULT_TIMEOUT, timeUnit);
    }

    @Override
    public boolean lock(String key, long expire, int retryTimes, TimeUnit timeUnit) {
        return tryLock(key, expire, retryTimes, DEFAULT_TIMEOUT, timeUnit);
    }

    @Override
    public boolean tryLock(String key, long timeout) {
        return tryLock(key, DEFAULT_EXPIRE_TIME, DEFAULT_RETRY_TIMES, timeout, DEFAULT_TIMEUNIT);
    }

    @Override
    public boolean tryLock(String key, long expire, long timeout) {
        return tryLock(key, expire, DEFAULT_RETRY_TIMES, timeout, DEFAULT_TIMEUNIT);
    }

    @Override
    public boolean tryLock(String key, long expire, long timeout, TimeUnit timeUnit) {
        return tryLock(key, expire, DEFAULT_RETRY_TIMES, timeout, timeUnit);
    }
}
