package cn.cjf.dao;

import cn.cjf.base.BaseTest;
import cn.cjf.domain.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserJpaDaoTest extends BaseTest {
    @Autowired
    UserJpaDao userDao;

    @Test
    public void testUserDao() {
        Assert.assertNotNull(userDao);

        User userTest = userDao.findUserByUsername("test");

        if (userTest == null) {
            User user = new User();
            user.setUsername("test");
            user.setPassword("123");
            userDao.save(user);

            Long id = user.getId();
            Assert.assertNotNull(id);

            userTest = userDao.findUserByUsername("test");
            Assert.assertNotNull(userTest);

            Assert.assertEquals(user.getUsername(), userTest.getUsername());
        } else {
            Assert.assertNotNull(userTest);
        }
    }
}
