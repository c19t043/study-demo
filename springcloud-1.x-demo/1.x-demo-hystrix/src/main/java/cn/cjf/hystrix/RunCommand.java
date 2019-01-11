package cn.cjf.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;

public class RunCommand extends HystrixCommand<String> {

    String msg;

    protected RunCommand(String msg) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("group-key"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("command-key"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("pool-key")));
        this.msg = msg;
    }

    @Override
    protected String run() throws Exception {
        System.out.println(msg);
        return "success";
    }
}