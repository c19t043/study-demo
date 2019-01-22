package cn.cjf.ok2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;

@RestController
@RequestMapping("/validate3")
@Validated
public class ValidateController3{
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateController3.class);


    @GetMapping("/param")
    @ResponseBody
    public String param(@RequestParam("email") @Email String email) {
        return email;
    }
}