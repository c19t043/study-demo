package cn.cjf.ok2.controller;

import cn.cjf.ok2.MainApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;

@SpringBootTest(classes = MainApplication.class)
@RunWith(SpringRunner.class)
public class CustomValidateControllerTest {

    @Autowired
    CustomValidateController customValidateController;

    @Test(expected = ConstraintViolationException.class)
    public void test() {
        String query1 = customValidateController.test("1");
        System.out.println(query1);
    }
}
