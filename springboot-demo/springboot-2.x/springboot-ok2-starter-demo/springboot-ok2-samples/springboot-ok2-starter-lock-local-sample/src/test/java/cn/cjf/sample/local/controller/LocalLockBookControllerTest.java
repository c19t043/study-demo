package cn.cjf.sample.local.controller;

import cn.cjf.sample.local.MainApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = MainApplication.class)
@RunWith(SpringRunner.class)
public class LocalLockBookControllerTest {

    @Autowired
    LocalLockBookController localLockBookController;

    @Test
    public void test() {
        try {
            String query = localLockBookController.query("1");
            System.out.println(query);
            String query1 = localLockBookController.query("1");
            System.out.println(query1);
        } catch (Exception e) {
            System.out.println("error:==================" + e.getMessage());
        }
    }
}
