package cn.cjf.ok2.redis.controller;

import cn.cjf.ok2.redis.lock.distribute.annotation.CacheLock;
import cn.cjf.ok2.redis.lock.distribute.annotation.CacheParam;
import cn.cjf.ok2.redis.lock.distribute.lock.DistributedLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books4")
public class DistributeLockBookController {

    @Autowired
    private DistributedLock distributedLock;

    @CacheLock(prefix = "books")
    @GetMapping
    public String query(@CacheParam(name = "token") @RequestParam String token) {
        return "success - " + token;
    }

    @CacheLock(prefix = "books2",expire = 3, timeout = 1)
    @GetMapping(value = "/books2")
    public String query2(@RequestParam String token) {
        return "success - " + token;
    }

    @GetMapping(value = "/books3")
    public String query3(@RequestParam String token) {
        try {
            if (distributedLock.tryLock(token,2000)) {
                System.out.println("获取到锁，执行业务逻辑");
            } else {
                System.out.println("没有获取到锁，执行逻辑");
            }
        } catch (Exception e) {

        } finally {
            System.out.println("释放锁");
            distributedLock.releaseLock(token);
        }
        return "success - " + token;
    }
}