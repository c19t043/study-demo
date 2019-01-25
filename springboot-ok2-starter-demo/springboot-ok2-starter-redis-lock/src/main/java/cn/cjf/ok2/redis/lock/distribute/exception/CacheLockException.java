package cn.cjf.ok2.redis.lock.distribute.exception;

public class CacheLockException extends RuntimeException {
    public CacheLockException() {
    }

    public CacheLockException(String message) {
        super(message);
    }
}
