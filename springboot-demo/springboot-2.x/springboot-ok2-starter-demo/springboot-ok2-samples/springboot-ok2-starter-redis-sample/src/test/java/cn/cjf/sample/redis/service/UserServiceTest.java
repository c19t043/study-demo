package cn.cjf.sample.redis.service;

import cn.cjf.sample.redis.MainApplication;
import cn.cjf.sample.redis.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class)
public class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    public void test() {
        userService.saveUser(new User(1, "text1"));

        User user = userService.findUserById(1);
        System.out.println("user:" + user);

        userService.deleteUser(1);
    }
}
