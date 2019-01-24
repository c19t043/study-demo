package cn.cjf.ok2.controller;

import cn.cjf.ok2.MainApplication;
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

    @Test(expected = RuntimeException.class)
    public void test() {
        String query = localLockBookController.query("1");
        System.out.println(query);
        String query1 = localLockBookController.query("1");
        System.out.println(query1);
    }
}
