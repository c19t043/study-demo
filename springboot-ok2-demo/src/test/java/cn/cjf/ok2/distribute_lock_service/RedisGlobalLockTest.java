package cn.cjf.ok2.distribute_lock_service;

import cn.cjf.ok2.MainApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = MainApplication.class)
@RunWith(SpringRunner.class)
public class RedisGlobalLockTest {
    @Autowired
    RedisGlobalLock redisGlobalLock;

    @Test
    public void test() {
// 1、获取分布式锁防止重复调用 =====================================================
        String key = "order:1";
        if (redisGlobalLock.lock(key)) {
            try {
                System.out.println("--处理业务---");
            } catch (Exception e) {
                throw e;
            } finally {
                // 4、释放分布式锁 ================================================================
                redisGlobalLock.unlock(key);
            }
        } else {
            System.out.println("获取锁失败");
        }
    }
}
