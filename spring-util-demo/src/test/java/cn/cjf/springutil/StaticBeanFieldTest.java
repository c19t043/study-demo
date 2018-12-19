package cn.cjf.springutil;

import java.beans.IntrospectionException;
import java.lang.reflect.Field;

public class StaticBeanFieldTest {
    public static String FIELD_NAME;
    public static int FIELD_AGE;

    /**
     * 测试反射给静态字段赋值
     */
    public static void main1(String[] args) throws IllegalAccessException, InstantiationException, IntrospectionException {
        Class<StaticBeanFieldTest> staticBeanFieldTestClass = StaticBeanFieldTest.class;
        StaticBeanFieldTest staticBeanFieldTest = staticBeanFieldTestClass.newInstance();
        Field[] declaredFields = staticBeanFieldTestClass.getDeclaredFields();
        for (Field field : declaredFields) {
            System.out.println(field.getName());
            Class<?> type = field.getType();
            if (type.equals(String.class)) {
                field.set(staticBeanFieldTest, "123");
            } else if (type.equals(int.class)) {
                field.set(staticBeanFieldTest, 456);
            }
            System.out.println(field.get(staticBeanFieldTest));
        }
    }

    /**
     * 测试静态字段赋值工具类
     */
    public static void main(String[] args) {
        StaticConstantLoadUtil.propertiesFilePath = "test";
        StaticConstantLoadUtil.autoSetValue(new Class[]{StaticBeanFieldTest.class});
        System.out.println(StaticBeanFieldTest.FIELD_NAME);
        System.out.println(StaticBeanFieldTest.FIELD_AGE);
    }
}
