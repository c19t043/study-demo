package cn.cjf.util;

import cn.cjf.util.vo.BaseVo;
import org.springframework.util.ReflectionUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenjunfan
 * @date 2019/4/10
 */
public class ObjectUtils {
    /**
     * 将对象转为map  List<object{id,xx}> -> map<id,object>
     */
    public static <T> Map<Serializable, T> transformObjectCollection2Map(List<T> list, Class<T> clazz) {
        final String idKey = "getId";
        Map<Serializable, T> map = new HashMap<>(list.size());

        list.forEach(bo -> {
            Object idValue = ReflectionUtils.invokeMethod(ReflectionUtils.findMethod(clazz, idKey), bo);
            if (idValue instanceof Serializable) {
                map.put((Serializable) idValue, bo);
            }
        });
        return map;
    }

    public static final String ID_KEY_NAME = "id";

    /**
     * 将对象转为map  List<object{id,xx}> -> map<id,object>
     */
    public static <T> Map<Long, T> transformObjectCollection2Map(List<T> list, String fieldKeyName) {
        Map<Long, T> map = new HashMap<>(list.size());
        if (list.isEmpty()) {
            return map;
        }
        T t = list.get(0);
        Class<?> clazz = t.getClass();
        try {
            PropertyDescriptor pd = new PropertyDescriptor(fieldKeyName, clazz);
            Object[] emptyArr = new Object[0];
            list.forEach(bo -> {
                try {
                    map.put((Long) pd.getReadMethod().invoke(bo, emptyArr), bo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return map;
    }

    public static <T extends BaseVo> Map<Serializable, T> transformObjectCollection2Map(List<T> list) {
        Map<Serializable, T> map = new HashMap<>(list.size());
        list.forEach(bo -> map.put(bo.getId(), bo));
        return map;
    }
}
