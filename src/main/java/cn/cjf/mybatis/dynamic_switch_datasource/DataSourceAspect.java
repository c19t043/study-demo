package cn.cjf.mybatis.dynamic_switch_datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class DataSourceAspect {

    @Pointcut("execution(* cn.cjf.*.*(..))")
    public void pointCut() {
    }


    @Before(value = "pointCut()")
    public void before(JoinPoint joinPoint) {
        CustomerContextHolder.setCustomerType("");

    }
}