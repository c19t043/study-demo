package cn.cjf.ok2.controller;

import cn.cjf.ok2.MainApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = MainApplication.class)
@RunWith(SpringRunner.class)
public class RabbitMqBookControllerTest {

    @Autowired
    RabbitMqBookController rabbitMqBookController;

    @Test
    public void test() {
        rabbitMqBookController.defaultMessage();
    }
}
