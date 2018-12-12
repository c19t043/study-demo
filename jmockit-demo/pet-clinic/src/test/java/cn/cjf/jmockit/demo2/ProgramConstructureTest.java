package cn.cjf.jmockit.demo2;

import cn.cjf.jmockit.demo1.HelloJMockit;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import org.junit.Assert;
import org.junit.Test;

//JMockit的程序结构

/**
 * JMockit的程序结构包含了
 * 测试属性或测试参数，
 * 测试方法，
 * 测试方法体中又包含
 * 录制代码块，重放测试逻辑，验证代码块。
 */
public class ProgramConstructureTest {

    // 这是一个测试属性
    /**
     * a)测试属性：即测试类的一个属性。它作用于测试类的所有测试方法。
     * 我们用@Mocked修饰了测试属性HelloJMockit helloJMockit，
     * 表示helloJMockit这个测试属性，它的实例化，属性赋值，方法调用的返回值全部由JMockit来接管，
     * 接管后，helloJMockit的行为与HelloJMockit类定义的不一样了，而是由录制脚本来定义了
     * <p>
     * 我们可以用JMockit的注解API来修饰它。这些API有@Mocked, @Tested, @Injectable,@Capturing。
     */
    @Mocked
    HelloJMockit helloJMockit;

    /**
     * Record-Replay-Verification
     * <p>
     * Record-Replay-Verification 是JMockit测试程序的主要结构。
     * Record: 即先录制某类/对象的某个方法调用，在当输入什么时，返回什么。
     * Replay: 即重放测试逻辑。
     * Verification: 重放后的验证。比如验证某个方法有没有被调用，调用多少次。
     * <p>
     * 其实，Record-Replay-Verification与JUnit程序的AAA(Arrange-Action-Assert)结构是一样的。
     * Record对应Arrange，先准备一些测试数据，测试依赖。Replay对应Action，即执行测试逻辑。
     * Verification对应Assert，即做测试验证。
     */

    @Test
    public void test1() {
        // 录制(Record)
        new Expectations() {
            {
                helloJMockit.sayHello();
                // 期待上述调用的返回是"hello,david"，而不是返回"hello,JMockit"
                result = "hello,david";
            }
        };
        // 重放(Replay)
        String msg = helloJMockit.sayHello();
        Assert.assertTrue(msg.equals("hello,david"));
        // 验证(Verification)
        new Verifications() {
            {
                helloJMockit.sayHello();

                times = 1;
            }
        };
    }

    /**
     * 给测试方法加参数，原本在JUnit中是不允许的，
     * 但是如果参数加了JMockit的注解API(@Mocked, @Tested, @Injectable,@Capturing)，则是允许的。
     *
     * @param helloJMockit b)测试参数：即测试方法的参数。它仅作用于当前测试方法。
     */
    @Test
    public void test2(@Mocked HelloJMockit helloJMockit /* 这是一个测试参数 */) {
        // 录制(Record)
        new Expectations() {
            {
                helloJMockit.sayHello();
                // 期待上述调用的返回是"hello,david"，而不是返回"hello,JMockit"
                result = "hello,david";
            }
        };
        // 重放(Replay)
        String msg = helloJMockit.sayHello();
        Assert.assertTrue(msg.equals("hello,david"));
        // 验证(Verification)
        new Verifications() {
            {
                helloJMockit.sayHello();
                // 验证helloJMockit.sayHello()这个方法调用了1次
                times = 1;
            }
        };
    }
}