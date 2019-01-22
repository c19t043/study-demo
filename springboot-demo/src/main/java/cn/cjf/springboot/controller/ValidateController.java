package cn.cjf.springboot.controller;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class ValidateController {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String paramCheck(@Length(min = 10) @RequestParam String name) {
        System.out.println(name);
        return null;
    }
}