package junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

//捆绑几个测试案例并且同时运行
//JUnit Suite Test
@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestJunit1.class, TestJunit2.class
})
public class JunitTestSuite {
    /**
     * Inside testPrintMessage()
     * Inside testSalutationMessage()
     */
}