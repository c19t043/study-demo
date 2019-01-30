package cn.cjf.ok2.controller;

import cn.cjf.ok2.MainApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = MainApplication.class)
@RunWith(SpringRunner.class)
public class DistributeLockBookControllerTest {
    @Autowired
    DistributeLockBookController distributeLockBookController;

    @Test
    public void test() {
        String query = distributeLockBookController.query("1");
        System.out.println(query);
        String query1 = distributeLockBookController.query("1");
        System.out.println(query1);
    }

    @Test
    public void test2() {
        String query = distributeLockBookController.query2("1");
        System.out.println(query);
        String query1 = distributeLockBookController.query2("1");
        System.out.println(query1);
    }
}
