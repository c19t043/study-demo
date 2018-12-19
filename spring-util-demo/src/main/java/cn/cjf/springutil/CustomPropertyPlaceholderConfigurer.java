package cn.cjf.springutil;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 替换原有配置文件加载类
 */
public class CustomPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
    private static Map<String, String> propertiesMap;

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);

        propertiesMap = new ConcurrentHashMap<>(16);
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String valueStr = resolvePlaceholder(keyStr, props);
            propertiesMap.put(keyStr, valueStr);
        }
    }

    public static String  getProperty(String name) {
        return propertiesMap.get(name);
    }
}
