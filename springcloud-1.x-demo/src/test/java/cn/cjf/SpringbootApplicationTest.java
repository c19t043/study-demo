package cn.cjf;

import cn.cjf.base.WebBaseTest;
import cn.cjf.domain.MyInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringbootApplicationTest extends WebBaseTest {

    @Test
    public void testHello() {
        ResponseEntity<String> response = restTemplate.getForEntity(baseUrl + "/hello", String.class);
        assertThat(response.getBody(), equalTo("Greetings from Spring Boot"));
    }

    @Value("${my.name}")
    String name;

    @Value("${my.age}")
    int age;

    @Autowired
    MyInfo myInfo;

    @Test
    public void test() {
        System.out.println(name);
        System.out.println(age);

        System.out.println(myInfo.getAge());
        System.out.println(myInfo.getName());

        assertThat(name, equalTo(myInfo.getName()));
    }

}

