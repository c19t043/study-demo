package cn.cjf.ok2.distribute_lock_annotation_aop;

import org.aspectj.lang.ProceedingJoinPoint;

public interface CacheKeyGenerator {

    /**
     * 获取AOP参数,生成指定缓存Key
     *
     * @param pjp PJP
     * @return 缓存KEY
     */
    String getLockKey(ProceedingJoinPoint pjp);
}