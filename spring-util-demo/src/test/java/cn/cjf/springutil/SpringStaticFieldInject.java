package cn.cjf.springutil;

import org.springframework.beans.factory.annotation.Value;

public class SpringStaticFieldInject {
    public static String FIELD_ID;

    @Value("${fieldId}")
    public void setFieldId(String fieldId) {
        FIELD_ID = fieldId;
    }
}
