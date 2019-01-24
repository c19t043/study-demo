package cn.cjf.ok2.service;

import cn.cjf.ok2.domain.redis.RedisUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisServiceTest {
    private static final Logger log = LoggerFactory.getLogger(RedisServiceTest.class);


    @Autowired
    private RedisService redisService;


    @Test
    public void get() {
        RedisUser redisUser = new RedisUser(5L, "u5", "p5");
        redisService.saveOrUpdate(redisUser);
        log.info("[saveOrUpdate] - [{}]", redisUser);
        final RedisUser user1 = redisService.get(5L);
        log.info("[get] - [{}]", user1);
        redisService.delete(5L);
    }
}

