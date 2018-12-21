package cn.cjf.springboot.controller;

import cn.cjf.springboot.entity.BookValidate;
import cn.cjf.springboot.entity.BookValidate2;
import cn.cjf.springboot.validate.DateTime;
import cn.cjf.springboot.validate.Groups;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * 参数校验
 *
 * @author Levin
 * @since 2018/6/04 0031
 */
@Validated
@RestController
public class ValidateController {

    @GetMapping("/test2")
    public String test2(@NotBlank(message = "name 不能为空")
                        @Length(min = 2, max = 10, message = "name 长度必须在 {min} - {max} 之间")
                                String name) {
        return "success";
    }

    @GetMapping("/test3")
    public String test3(@Validated BookValidate book) {
        return "success";
    }

    @GetMapping("/test")
    public String test(@DateTime(message = "您输入的格式错误，正确的格式为：{format}", format = "yyyy-MM-dd HH:mm")
                               String date) {
        return "success";
    }

    @GetMapping("/insert")
    public String insert(@Validated(value = Groups.Default.class) BookValidate2 book) {
        return "insert";
    }

    @GetMapping("/update")
    public String update(@Validated(value = {Groups.Default.class, Groups.Update.class}) BookValidate2 book) {
        return "update";
    }
}