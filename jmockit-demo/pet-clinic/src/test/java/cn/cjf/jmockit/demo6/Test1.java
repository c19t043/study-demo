package cn.cjf.jmockit.demo6;

import mockit.Expectations;

public class Test1 {
    {
        new Expectations() {
            // 这是一个Expectations匿名内部类
            {
                // 这是这个内部类的初始化代码块，我们在这里写录制脚本，脚本的格式要遵循下面的约定
                //方法调用(可是类的静态方法调用，也可以是对象的非静态方法调用)
                //result赋值要紧跟在方法调用后面
                //...其它准备录制脚本的代码
                //方法调用
                //result赋值
            }
        };

        //还可以再写new一个Expectations，只要出现在重放阶段之前均有效。
        new Expectations() {

            {
                //...录制脚本
            }
        };
    }
}
