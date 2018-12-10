package cn.cjf.router.manager.mapper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DtqMapper {
    private static Map<String, String> base2RouterMapper = new ConcurrentHashMap<>();
    private static Map<String, String> major2RouterMapper = new ConcurrentHashMap<>();

    /**
     * 保存基站到路由映射
     */
    public static void putBase2RouterMapper(String key, String value) {
        base2RouterMapper.put(key, value);
    }

    /**
     * 获取基站到路由映射
     */
    public static String getBase2RouterMapper(String key) {
        return base2RouterMapper.get(key);
    }

    /**
     * 保存主讲到路由映射
     */
    public static void putMajor2RouterMapper(String key, String value) {
        major2RouterMapper.put(key, value);
    }

    /**
     * 获取主讲到路由映射
     */
    public static String getMajor2RouterMapper(String key) {
        return major2RouterMapper.get(key);
    }
}
