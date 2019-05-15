package cn.cjf.web.controller;

import cn.cjf.web.commons.ConstString;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/spring/session", produces = {ConstString.APP_JSON_UTF_8})
public class SpringSessionDemoController {

    @RequestMapping(value = "/setSession.do", method = RequestMethod.GET)
    public void setSession(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String value = request.getParameter("value");
        request.getSession().setAttribute(name, value);
    }

    @RequestMapping(value = "/getSession.do", method = RequestMethod.GET)
    public void getInterestPro(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        System.out.println("------" + request.getSession().getAttribute(name));
    }

    @RequestMapping(value = "/removeSession.do", method = RequestMethod.GET)
    public void removeSession(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        request.getSession().removeAttribute(name);
    }
}