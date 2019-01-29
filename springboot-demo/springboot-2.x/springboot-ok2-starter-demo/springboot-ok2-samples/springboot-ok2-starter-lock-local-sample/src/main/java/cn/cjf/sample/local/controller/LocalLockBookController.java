package cn.cjf.sample.local.controller;

import cn.cjf.ok2.lock.local.annotation.LocalLock;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * BookController
 */
@RestController
@RequestMapping("/books")
public class LocalLockBookController {

    @LocalLock(key = "book:arg[0]")
    @GetMapping
    public String query(@RequestParam String token) {
        return "success - " + token;
    }
}