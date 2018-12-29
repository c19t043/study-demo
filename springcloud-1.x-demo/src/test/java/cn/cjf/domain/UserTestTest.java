package cn.cjf.domain;

import cn.cjf.base.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserTestTest extends BaseTest{
    @Autowired
    UserTest userTest;

    @Test
    public void testUserTest() {
        Assert.assertNotNull(userTest);
        Assert.assertEquals(userTest.getAge(), 12);
        Assert.assertEquals(userTest.getName(), "test");
    }
}
