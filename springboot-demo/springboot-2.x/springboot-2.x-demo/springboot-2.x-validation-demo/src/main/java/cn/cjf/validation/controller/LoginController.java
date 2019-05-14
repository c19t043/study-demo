package cn.cjf.validation.controller;

import cn.cjf.validation.Response.ResponseResult;
import cn.cjf.validation.domain.LoginForm;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * @author chenjunfan
 * @date 2019/5/14
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    @GetMapping("/index.html")
    public ModelAndView index() {
        return new ModelAndView("login");
    }

    @PostMapping("/login.json")
    @ResponseBody
    public ResponseResult login(@Valid LoginForm loginForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                return ResponseResult.fail(error.getDefaultMessage());
            }
        }
        return ResponseResult.success();
    }
}
