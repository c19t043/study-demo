package cn.cjf.demo1;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestService {

    private TestDao testDao;

    public String testServiceMethod(Long id) {
        TestEntity entity = testDao.getById(id);

        if (entity.getUserId() == 1061L) {
            return "携程旅行网";
        }

        if (entity.getUserId() == 1609L) {
            return "云娱";
        }

        return "";

    }

    public interface TestDao {
        TestEntity getById(Long id);
    }

    public class TestEntity {

        Long userId;
        String name;

        public Long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public class TestMock {

        @Test
        public void testMock() {

            //1.创建dao的mock

            TestDao testDao = mock(TestDao.class);

            TestEntity entity1 = new TestEntity();

            entity1.setUserId(1061L);

            entity1.setName("XX旅行网");

            TestEntity entity2 = new TestEntity();

            entity2.setUserId(1609L);

            entity2.setName("X娱");

            //2.定义行为，当调用dao的getById方法并且传入xx参数，则返回对应的entity

            when(testDao.getById(1061L)).thenReturn(entity1);

            when(testDao.getById(1609L)).thenReturn(entity2);

            //3.注入dao到service

            TestService testService = new TestService();

            try {

                Field declaredField = TestService.class.getDeclaredField("testDao");

                declaredField.setAccessible(true);

//                ReflectionUtils.setField(declaredField, testService, testDao);

            } catch (Exception e) {

                e.printStackTrace();

                Assert.fail();

            }

            //4.验证service功能

            Assert.assertTrue(testService.testServiceMethod(1061L).equals("XX旅行网"));

            Assert.assertTrue(testService.testServiceMethod(1609L).equals("X娱"));

        }
    }
}