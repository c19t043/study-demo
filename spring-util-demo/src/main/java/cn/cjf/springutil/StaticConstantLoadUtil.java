package cn.cjf.springutil;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class StaticConstantLoadUtil {
    /**
     * properties配置文件classpath路径,如果test.properties,路径test
     */
    public static String propertiesFilePath;
    private static ResourceBundle resource;

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
            if (resource == null) {
                resource = ResourceBundle.getBundle(propertiesFilePath);
            }
            //转为小写key获取，如果为null,_转为.获取
            String keyLower = key.toLowerCase();
            String result = null;
            try {
                result = resource.getString(keyLower);
            } catch (MissingResourceException e) {

            }
            if (StringUtils.isBlank(result)) {
                result = resource.getString(keyLower.replaceAll("_", "."));
            }
            return result;
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
