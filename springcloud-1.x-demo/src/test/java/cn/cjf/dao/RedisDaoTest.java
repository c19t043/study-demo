package cn.cjf.dao;

import cn.cjf.base.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RedisDaoTest  extends BaseTest {
    @Autowired
    RedisDao redisDao;

    @Test
    public void testRedisDao(){
        Assert.assertNotNull(redisDao);

        redisDao.setKey("name","test");
        redisDao.setKey("age","12");

        System.out.println(redisDao.getValue("name"));
        System.out.println(redisDao.getValue("age"));
    }
}
