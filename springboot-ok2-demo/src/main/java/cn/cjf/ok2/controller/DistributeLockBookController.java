package cn.cjf.ok2.controller;

import cn.cjf.ok2.distribute_lock.CacheLock;
import cn.cjf.ok2.distribute_lock.CacheParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books4")
public class DistributeLockBookController {

    @CacheLock(prefix = "books")
    @GetMapping
    public String query(@CacheParam(name = "token") @RequestParam String token) {
        return "success - " + token;
    }

    @CacheLock(prefix = "books2")
    @GetMapping(value = "/books2")
    public String query2(@RequestParam String token) {
        return "success - " + token;
    }
}