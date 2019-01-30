package cn.cjf.controller;

@Controller
public class IndexController {
    @RequestMapping("/index")
    public String index() {
        return "index";
    }
}