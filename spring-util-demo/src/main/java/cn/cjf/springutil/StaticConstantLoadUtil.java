package cn.cjf.springutil;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ResourceBundle;

public class StaticConstantLoadUtil {
    /**
     * properties配置文件classpath路径,如果test.properties,路径test
     */
    public static String propertiesFilePath;

    /**
     * 静态字段，自动设置
     */
    public static void autoSetValue(Class<?>[] clazzs) {
        validateNotInterface(clazzs);

        for (Class clazz : clazzs) {
            reflectSetValue(clazz);
        }
    }

    /**
     * 反射赋值
     */
    private static void reflectSetValue(Class clazz) {
        try {
            Object obj = clazz.newInstance();
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {
//                System.out.println(field.getName());
                setValue(obj, field);
//                System.out.println(field.get(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setValue(Object entity, Field field) throws IllegalAccessException {
        Class<?> type = field.getType();
        String propertyValue = getValue(field.getName());
        if (type.equals(String.class)) {
            field.set(entity, propertyValue);
        } else if (type.equals(int.class)) {
            field.set(entity, Integer.parseInt(propertyValue));
        } else if (type.equals(Integer.class)) {
            field.set(entity, Integer.valueOf(propertyValue));
        } else if (type.equals(long.class)) {
            field.set(entity, Long.parseLong(propertyValue));
        } else if (type.equals(Long.class)) {
            field.set(entity, Long.valueOf(propertyValue));
        } else if (type.equals(double.class)) {
            field.set(entity, Double.parseDouble(propertyValue));
        } else if (type.equals(Double.class)) {
            field.set(entity, Double.valueOf(propertyValue));
        }
    }


    private static String getValue(String key) {
        if (StringUtils.isNotBlank(propertiesFilePath)) {
            ResourceBundle resource = ResourceBundle.getBundle(propertiesFilePath);
            return resource.getString(key.toLowerCase());
        } else {
            return CustomPropertyPlaceholderConfigurer.getProperty(key);
        }
    }

    /**
     * 校验类型不是接口
     */
    private static void validateNotInterface(Class<?>[] clazzs) {
        for (Class clazz : clazzs) {
            if (clazz.isInterface()) {
                throw new IllegalArgumentException("传入参数不能为接口");
            }
        }
    }
}
