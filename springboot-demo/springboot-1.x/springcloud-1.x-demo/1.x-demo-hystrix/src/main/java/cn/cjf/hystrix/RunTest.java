package cn.cjf.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import rx.Observable;
import rx.Observer;

public class RunTest {

    static class RunCommand extends HystrixCommand<String>{

        String msg;

        protected RunCommand(String msg) {
            super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
            this.msg = msg;
        }

        @Override
        protected String run() throws Exception {
            System.out.println(msg);
            return "success";
        }
    }

    public static void main(String[] args) {
        RunCommand c1 = new RunCommand("excute");
        c1.execute();

        RunCommand c2 = new RunCommand("queue");
        c2.queue();

        RunCommand c3 = new RunCommand("observe");
        c3.observe();

        RunCommand c4 = new RunCommand("toObservable");
        //调用toObservable方法后，命令不会马上执行
        Observable<String> ob = c3.toObservable();
        //进行订阅，此时执行
        ob.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(String s) {

            }
        });
    }
}
