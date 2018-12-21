package cn.cjf.springboot.controller;

import cn.cjf.springboot.annotation.LocalLock;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * BookController
 * 在接口上添加 @LocalLock(key = "book:arg[0]")；意味着会将 arg[0] 替换成第一个参数的值，生成后的新 key 将被缓存起来；
 *
 * @author Levin
 * @since 2018/6/06 0031
 */
@RestController
@RequestMapping("/books")
public class BookLockedController2 {

    @LocalLock(key = "book:arg[0]")
    @GetMapping
    public String query(@RequestParam String token) {
        return "success - " + token;
    }
}