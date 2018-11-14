package cn.cjf.gateway.utils;

import cn.cjf.gateway.annotation.RequiredAnnotation;
import cn.cjf.gateway.config.ConstantConfig;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 转换工具类
 *
 * @author chenjunfan 176158750@qq.com
 */
public class ConverterUtil {
    private static Logger log = LoggerFactory.getLogger(ConverterUtil.class);

    /**
     * 将对象转成xml
     */
    public static String convertObject2Xml(Object o) {
        Map<String, Object> fieldMap = convertObject2Map(o);
        if (fieldMap == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder("<xml>");
        for (Map.Entry<String, Object> entry : fieldMap.entrySet()) {
            sb.append("<").append(entry.getKey()).append(">")
                    .append("<!CDATA[").append(entry.getValue()).append("]>")
                    .append("</").append(entry.getKey()).append(">");
        }
        sb.append("</xml>");

        return sb.toString();
    }

    /**
     * 将xml转为指定对象
     *
     * @param xml xml字符串
     * @param clazz 指定要转换对象
     */
    public static <T> T convertXml2Object(String xml, Class<T> clazz) {
        Map<String, String> map = parseXml(xml);
        if(map == null){
            return null;
        }
        return JSON.parseObject(JSON.toJSONString(map),clazz);
    }

    /**
     * 将xml转换为Map
     *
     * @param xml xml字符串
     * @return Map
     */
    private static Map<String, String> parseXml(String xml) {
        Map<String, String> map = new HashMap<>(ConstantConfig.MAP_INITIAL_CAPACITY);
        Document document;
        try {
            document = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            log.error("XML转换为Map,失败,值为:{}", xml);
            return null;
        }
        Element root = document.getRootElement();
        List<Element> elementList = root.elements();

        for (Element e : elementList) {
            map.put(e.getName(), e.getText());
        }
        return map;
    }

    /**
     * 将对象转换为Map
     */
    private static Map<String, Object> convertObject2Map(Object o) {
        Class<?> clazz = o.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        Map<String, Object> fieldMap = new HashMap<>(ConstantConfig.MAP_INITIAL_CAPACITY);

        for (Field field : declaredFields) {
            if (!field.isAccessible()) {
                field.setAccessible(Boolean.TRUE);
            }
            Object value;
            try {
                value = field.get(o);
            } catch (IllegalAccessException e) {
                log.error("{}对象转换为Map,反射获取对象{}字段值,失败", clazz.getName(), field.getName());
                return null;
            }
            //校验
            if (validateFieldWithRequired(field)) {
                if (value == null) {
                    log.error("{}对象转换为Map,{}必填字段不能为空,对象值{}", new Object[]{clazz.getName(), field.getName(), o.toString()});
                    return null;
                }
            }
            JSONField jsonField = field.getAnnotation(JSONField.class);
            if (jsonField != null) {
                String name = jsonField.name();
                if (StringUtils.isNotBlank(name)) {
                    fieldMap.put(name, value);
                }
            }
        }
        return fieldMap;
    }

    /**
     * 验证字段是否必填
     *
     * @param field 反射字段
     * @return TRUE/FALSE
     */
    private static boolean validateFieldWithRequired(Field field) {
        RequiredAnnotation requiredAnnotation = field.getAnnotation(RequiredAnnotation.class);
        if (requiredAnnotation == null) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
