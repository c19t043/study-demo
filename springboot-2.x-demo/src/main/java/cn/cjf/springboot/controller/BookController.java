package cn.cjf.springboot.controller;

import cn.cjf.springboot.annotation.CacheLock;
import cn.cjf.springboot.annotation.CacheParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * BookController
 *
 * @author Levin
 * @since 2018/6/06 0031
 */
@RestController
@RequestMapping("/books")
public class BookController {
    /**
     * 在接口上添加 @CacheLock(prefix = "books")，然后动态的值可以加上@CacheParam；
     * 生成后的新 key 将被缓存起来；（如：该接口 token = 1，那么最终的 key 值为 books:1，如果多个条件则依次类推）
     */
    @CacheLock(prefix = "books")
    @GetMapping
    public String query(@CacheParam(name = "token") @RequestParam String token) {
        return "success - " + token;
    }

}