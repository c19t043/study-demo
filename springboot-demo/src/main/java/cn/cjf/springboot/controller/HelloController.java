package cn.cjf.springboot.controller;

import cn.cjf.springboot.bean.Book;
import cn.cjf.springboot.bean.Person;
import cn.cjf.springboot.bean.PersonAddView;
import cn.cjf.springboot.bean.PersonModifyView;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.groups.Default;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello world";
    }

    /**
     * 添加Book对象
     *
     * @param book
     */
    @RequestMapping(value = "/book", method = RequestMethod.POST)
    public void addBook(@RequestBody @Valid Book book) {
        System.out.println(book.toString());
    }

    /**
     * 添加一个Person对象
     * 此处启用PersonAddView 这个验证规则
     * 备注：此处@Validated(PersonAddView.class) 表示使用PersonAndView这套校验规则，若使用@Valid 则表示使用默认校验规则，
     * 若两个规则同时加上去，则只有第一套起作用
     */
    @RequestMapping(value = "/person", method = RequestMethod.POST)
    public void addPerson(@RequestBody @Validated({PersonAddView.class, Default.class}) Person person) {
        System.out.println(person.toString());
    }

    /**
     * 修改Person对象
     * 此处启用PersonModifyView 这个验证规则
     */
    @RequestMapping(value = "/person", method = RequestMethod.PUT)
    public void modifyPerson(@RequestBody @Validated(value = {PersonModifyView.class}) Person person) {
        System.out.println(person.toString());
    }
}
