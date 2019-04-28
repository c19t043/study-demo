package cn.cjf.util;

import org.springframework.context.ApplicationContext;

/**
 * spring工具类
 *
 * @author CJF
 * @date 2018-09-19
 */
public final class SpringUtil {
    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringUtil.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
}
