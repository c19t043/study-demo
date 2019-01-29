package cn.cjf.ok2.redis.lock.distribute;

import cn.cjf.ok2.redis.lock.distribute.annotation.CacheLock;
import cn.cjf.ok2.redis.lock.distribute.exception.CacheLockException;
import cn.cjf.ok2.redis.lock.distribute.keygenerator.CacheKeyGenerator;
import cn.cjf.ok2.redis.lock.distribute.lock.DistributedLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * redis 方案
 */
@Aspect
@Configuration
@AutoConfigureAfter(value = {DistributedLock.class, CacheKeyGenerator.class})
public class DistributeLockMethodInterceptor {

    @Resource
    private CacheKeyGenerator cacheKeyGenerator;
    @Resource
    private DistributedLock distributedLock;


    @Around("execution(public * *(..)) && @annotation(cn.cjf.ok2.redis.lock.distribute.annotation.CacheLock)")
    public Object interceptor(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        CacheLock lock = method.getAnnotation(CacheLock.class);
        if (StringUtils.isEmpty(lock.prefix())) {
            throw new RuntimeException("lock key don't null...");
        }
        String lockKey = cacheKeyGenerator.getLockKey(pjp);
        try {
            int expire = lock.expire() > 0 ? lock.expire() : 0;
            int retry = lock.retryTimes() > 0 ? lock.retryTimes() : 0;
            long timeout = lock.timeout() > 0 ? lock.timeout() : 0;
            // 采用原生 API 来实现分布式锁
            final Boolean success = distributedLock.tryLock(lockKey, expire, retry, timeout, lock.timeUnit());
            if (!success) {
                // TODO 按理来说 我们应该抛出一个自定义的 CacheLockException 异常;这里偷下懒
                throw new CacheLockException("请勿重复请求");
            }
            try {
                return pjp.proceed();
            } catch (Throwable throwable) {
                throw new CacheLockException("系统异常");
            }
        } finally {
            // TODO 如果演示的话需要注释该代码;实际应该放开
            distributedLock.releaseLock(lockKey);
        }
    }
}