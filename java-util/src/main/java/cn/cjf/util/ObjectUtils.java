package cn.cjf.util;

import cn.cjf.util.vo.BaseVo;
import org.springframework.util.ReflectionUtils;

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

    public static <T extends BaseVo> Map<Serializable, T> transformObjectCollection2Map(List<T> list) {
        Map<Serializable, T> map = new HashMap<>(list.size());
        list.forEach(bo -> map.put(bo.getId(), bo));
        return map;
    }
}
