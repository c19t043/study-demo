package cn.cjf.springboot.controller;

import cn.cjf.springboot.properties.MyProperties1;
import cn.cjf.springboot.properties.MyProperties2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Levin
 * @since 2018/4/23 0023
 */
@RequestMapping("/properties")
@RestController
public class PropertiesController {

    private static final Logger log = LoggerFactory.getLogger(PropertiesController.class);

    @Autowired
    private MyProperties1 myProperties1;

    @Autowired
    private MyProperties2 myProperties2;

//    @Autowired
//    public PropertiesController(MyProperties1 myProperties1) {
//        this.myProperties1 = myProperties1;
//    }

    @GetMapping("/1")
    public MyProperties1 myProperties1() {
        log.info("=================================================================================================");
        log.info(myProperties1.toString());
        log.info("=================================================================================================");
        return myProperties1;
    }

    @GetMapping("/2")
    public MyProperties2 myProperties2() {
        log.info("=================================================================================================");
        log.info(myProperties2.toString());
        log.info("=================================================================================================");
        return myProperties2;
    }


    @GetMapping("/demo1")
    public String demo1() {
        return "Hello battcn";
    }
}