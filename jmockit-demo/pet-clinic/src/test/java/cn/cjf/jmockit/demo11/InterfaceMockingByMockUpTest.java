package cn.cjf.jmockit.demo11;

import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.Tested;
import org.junit.Assert;
import org.junit.Test;

//用MockUp来mock接口
public class InterfaceMockingByMockUpTest {
    @Test
    public void testInterfaceMockingByMockUp() {
        AnOrdinaryInterface anOrdinaryInterface = new AnOrdinaryInterface() {
            @Override
            public int method1() {
                return 0;
            }

            @Override
            public int method2() {
                return 0;
            }
        };

        new MockUp<AnOrdinaryInterface>(AnOrdinaryInterface.class) {
            // 对方法Mock
            @Mock
            public int method1() {
                return 10;
            }
 
            @Mock
            public int method2() {
                return 20;
            }
        }.;
 
        Assert.assertTrue(anOrdinaryInterface.method1() == 10);
        Assert.assertTrue(anOrdinaryInterface.method2() == 20);
    }
}