package cn.cjf.web.util;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Created by Mark on 2018/4/21.
 * RedisPool封装的API
 */

@Slf4j
public class RedisPoolUtil {

    //设置key的有效期
    public static Long expire(String key, int seconds) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = RedisPool.getJedis();//从自定义RedisPool连接池获取一个实例
            result = jedis.expire(key, seconds);
        } catch (Exception e) {
            log.error("expire key:{} error", key, e);
            RedisPool.returnBrokenResource(jedis);//发生错误回收
            return result;
        }
        RedisPool.returnResource(jedis);//正常回收
        return result;
    }

    //设置key时间并且设置过期时间
    public static String setEx(String key, String value, int seconds) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.setex(key, seconds, value);
        } catch (Exception e) {
            log.error("expire key:{} value:{} error", key, value, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    //设置一个key
    public static String set(String key, String value) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.set(key, value);
        } catch (Exception e) {
            log.error("expire key:{} value:{} error", key, value, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    //获取一个key
    public static String get(String key) {
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.get(key);
        } catch (Exception e) {
            log.error("expire key:{} value:{} error", key, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    //删除一个key
    public static Long del(String key) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.del(key);
        } catch (Exception e) {
            log.error("expire key:{} value:{} error", key, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    public static Long lpush(String key, String... strings) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.lpush(key, strings);
        } catch (Exception e) {
            log.error("expire key:{} value:{} error", key, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    public static List<String> lrang(String key) {
        Jedis jedis = null;
        List<String> result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.lrange(key, 0, -1);
        } catch (Exception e) {
            log.error("expire key:{} value:{} error", key, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    //调用测试
    public static void main(String[] args) {
        String result = RedisPoolUtil.set("mark", "25");
        result = RedisPoolUtil.get("mark");
        result = RedisPoolUtil.setEx("mark2", "23", 20);
        Long resultL = RedisPoolUtil.expire("mark", 20);
        resultL = RedisPoolUtil.del("mark");
        resultL = RedisPoolUtil.del("marks");
        System.out.println("补充：使用redis缓存数据时候，最好加上过期时间，因为redis缓存需要占用内存空间");
    }

}