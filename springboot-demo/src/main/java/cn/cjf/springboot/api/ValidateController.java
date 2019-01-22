package cn.cjf.springboot.api;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;

@Controller
@RequestMapping("/validate")
@Validated
public class ValidateController {

    @GetMapping("/param")
    @ResponseBody
    public String param(@RequestParam("group") @Email String group,
                        @RequestParam("userid") Integer userid) {
        return group + ":" + userid;
    }

    @PostMapping("/form")
    @ResponseBody
    public FormRequest form(@Validated FormRequest form) {
        return form;
    }
    @PostMapping("/json")
    @ResponseBody
    public JsonRequest json(@Validated @RequestBody JsonRequest request) {

        return request;
    }
}