package cn.cjf.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

public class MyCommand extends HystrixCommand<String> {
    protected MyCommand(boolean isTimeOut) {
        super(Setter
                .withGroupKey(
                        HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        //设置超时时间
                        .withExecutionTimeoutInMilliseconds(500)));
    }

    @Override
    protected String run() throws Exception {
        return null;
    }

    @Override
    protected String getCacheKey() {
        return super.getCacheKey();
    }
}
