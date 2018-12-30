package cn.cjf.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RestTestController {
    @GetMapping("/testRest")
    public String testRest() {
        RestTemplate rest = new RestTemplate();
        return rest.getForObject("https://www.baidu.com/", String.class);
    }
}
