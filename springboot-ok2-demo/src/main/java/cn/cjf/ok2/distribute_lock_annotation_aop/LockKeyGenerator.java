package cn.cjf.ok2.distribute_lock_annotation_aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 上一章说过通过接口注入的方式去写不同的生成规则;
 */
@Component
public class LockKeyGenerator implements CacheKeyGenerator {

    @Override
    public String getLockKey(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        CacheLock lockAnnotation = method.getAnnotation(CacheLock.class);
        final Object[] args = pjp.getArgs();
        final Parameter[] parameters = method.getParameters();
        StringBuilder builder = new StringBuilder();
        // TODO 默认解析方法里面带 CacheParam 注解的属性,如果没有尝试着解析实体对象中的
        for (int i = 0; i < parameters.length; i++) {
            final CacheParam annotation = parameters[i].getAnnotation(CacheParam.class);
            if (annotation == null) {
                continue;
            }
            builder.append(lockAnnotation.delimiter()).append(args[i]);
        }
        return lockAnnotation.prefix() + builder.toString();
//        if (StringUtils.isEmpty(builder.toString())) {
//            for (int i = 0; i < parameters.length; i++) {
//                final Object object = args[i];
//                String value = object == null ? "null" : object.toString();
//                builder.append(lockAnnotation.delimiter()).append(value);
//            }
//        }
//        if (StringUtils.isEmpty(builder.toString())) {
//            final Annotation[][] parameterAnnotations = method.getParameterAnnotations();
//            for (int i = 0; i < parameterAnnotations.length; i++) {
//                final Object object = args[i];
//                final Field[] fields = object.getClass().getDeclaredFields();
//                for (Field field : fields) {
//                    final CacheParam annotation = field.getAnnotation(CacheParam.class);
//                    if (annotation == null) {
//                        continue;
//                    }
//                    field.setAccessible(true);
//                    builder.append(lockAnnotation.delimiter()).append(ReflectionUtils.getField(field, object));
//                }
//            }
//        }

    }
}