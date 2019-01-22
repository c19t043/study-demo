package cn.cjf.springboot;

import cn.cjf.springboot.bean.Person1;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * springboot单元测试
 *
 * 可以在测试期间很方便的使用类似编码一样进行自动注入等容器功能
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootMainApplicationTest {
    @Autowired
    Person1 person;

    @Test
    public void contextLoads(){
        System.out.println(person);
    }


}
