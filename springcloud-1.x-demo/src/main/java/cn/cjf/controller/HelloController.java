package cn.cjf.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注解@RestController 相当于@Controller、@ResponseBody之和
 */
@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String index() {
        return "Greetings from Spring Boot";
    }
}
