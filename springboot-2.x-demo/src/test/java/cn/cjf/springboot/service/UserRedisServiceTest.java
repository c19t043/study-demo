package cn.cjf.springboot.service;

import cn.cjf.springboot.entity.UserRedis;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRedisServiceTest {
    private static final Logger log = LoggerFactory.getLogger(UserRedisServiceTest.class);


    @Autowired
    private UserRedisService userService;

    /**
     * 启动测试类，结果和我们期望的一致，可以看到增删改查中，查询是没有日志输出的，
     * 因为它直接从缓存中获取的数据，而添加、修改、删除都是会进入方法内执行具体的业务代码，
     * 然后通过切面去删除掉Redis中的缓存数据。其中 # 号代表这是一个 SpEL 表达式，此表达式可以遍历方法的参数对象，
     * 具体语法可以参考 Spring 的相关文档手册。
     */
    /**
     * 其它类型
     * <p>
     * 下列的就是Redis其它类型所对应的操作方式
     * <p>
     * opsForValue： 对应 String（字符串）
     * opsForZSet： 对应 ZSet（有序集合）
     * opsForHash： 对应 Hash（哈希）
     * opsForList： 对应 List（列表）
     * opsForSet： 对应 Set（集合）
     * opsForGeo： 对应 GEO（地理位置）
     */
    /**
     * 根据条件操作缓存
     * 根据条件操作缓存内容并不影响数据库操作，条件表达式返回一个布尔值，true/false，当条件为true，则进行缓存操作，否则直接调用方法执行的返回结果。
     * <p>
     * 长度： @CachePut(value = "user", key = "#user.id",condition = "#user.username.length() < 10") 只缓存用户名长度少于10的数据
     * 大小： @Cacheable(value = "user", key = "#id",condition = "#id < 10") 只缓存ID小于10的数据
     * 组合： @Cacheable(value="user",key="#user.username.concat(##user.password)")
     * 提前操作： @CacheEvict(value="user",allEntries=true,beforeInvocation=true) 加上beforeInvocation=true后，不管内部是否报错，缓存都将被清除，默认情况为false
     */
    /**
     * 注解介绍
     *
     * @Cacheable(根据方法的请求参数对其结果进行缓存) key： 缓存的 key，可以为空，如果指定要按照 SpEL 表达式编写，如果不指定，则缺省按照方法的所有参数进行组合（如：@Cacheable(value="user",key="#userName")）
     * value： 缓存的名称，在 Spring 配置文件中定义，必须指定至少一个（如：@Cacheable(value="user") 或者 @Cacheable(value={"user1","use2"})）
     * condition： 缓存的条件，可以为空，使用 SpEL 编写，返回 true 或者 false，只有为 true 才进行缓存（如：@Cacheable(value = "user", key = "#id",condition = "#id < 10")）
     * @CachePut(根据方法的请求参数对其结果进行缓存，和 @Cacheable 不同的是，它每次都会触发真实方法的调用)
     * <p>
     * key： 同上
     * value： 同上
     * condition： 同上
     * @CachEvict(根据条件对缓存进行清空) key： 同上
     * value： 同上
     * condition： 同上
     * allEntries： 是否清空所有缓存内容，缺省为 false，如果指定为 true，则方法调用后将立即清空所有缓存（如：@CacheEvict(value = "user", key = "#id", allEntries = true)）
     * beforeInvocation： 是否在方法执行前就清空，缺省为 false，如果指定为 true，则在方法还没有执行的时候就清空缓存，缺省情况下，如果方法执行抛出异常，则不会清空缓存（如：@CacheEvict(value = "user", key = "#id", beforeInvocation = true)）
     */
    @Test
    public void get() {
        final UserRedis user = userService.saveOrUpdate(new UserRedis(5L, "u5", "p5"));
        log.info("[saveOrUpdate] - [{}]", user);
        final UserRedis user1 = userService.get(5L);
        log.info("[get] - [{}]", user1);
        userService.delete(5L);
    }
}
