package cn.cjf.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class FreemarkerController {
    @RequestMapping("/freemarkerIndex")
    public String index(Map<String, Object> result) {
        result.put("nickname", "tEngSHe789");
        result.put("old", "18");
        result.put("my Blog", "HTTPS://blog.tengshe789.tech/");
        List<String> listResult = new ArrayList<String>();
        listResult.add("guanyu");
        listResult.add("zhugeliang");
        result.put("listResult", listResult);
        return "index";
    }
}
