package cn.cjf.jmockit.demo8;

import mockit.Verifications;

public class Test1 {
    {
        new Verifications() {
            // 这是一个Verifications匿名内部类
            {
                // 这个是内部类的初始化代码块，我们在这里写验证脚本，脚本的格式要遵循下面的约定
                //方法调用(可是类的静态方法调用，也可以是对象的非静态方法调用)
                //times/minTimes/maxTimes 表示调用次数的限定要求。赋值要紧跟在方法调用后面，也可以不写（表示只要调用过就行，不限次数）
                //...其它准备验证脚本的代码
                //方法调用
                //times/minTimes/maxTimes 赋值
            }
        };

        //还可以再写new一个Verifications，只要出现在重放阶段之后均有效。
        new Verifications() {

            {
                //...验证脚本
            }
        };
    }
}
