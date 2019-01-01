package cn.cjf.controller;

import cn.cjf.Application;
import cn.cjf.service.HiService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class HiServiceTest {
    @Autowired
    HiService hiService;

    @Test
    public void testHiService(){
        Assert.assertNotNull(hiService);

        String test = hiService.sayHi("test");

        Assert.assertNotNull(test);

        System.out.println(test);
    }
}
