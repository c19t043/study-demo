package cn.cjf.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RibbonServiceTest{

    @Autowired
    RibbonService ribbonService;

    @Test
    public void testRibbonService(){
        Assert.assertNotNull(ribbonService);

        System.out.println(ribbonService.hi("离别"));
    }

}