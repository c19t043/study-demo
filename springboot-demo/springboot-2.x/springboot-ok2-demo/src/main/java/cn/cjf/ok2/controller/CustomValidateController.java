package cn.cjf.ok2.controller;

import cn.cjf.ok2.validate.DateTime;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/validate2")
public class CustomValidateController {

    @GetMapping("/test")
    public String test(@DateTime(message = "您输入的格式错误，正确的格式为：{format}",
            format = "yyyy-MM-dd HH:mm") String date) {
        return "success";
    }
}
