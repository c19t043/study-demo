package cn.cjf.thread.demo2;

import java.util.concurrent.TimeUnit;

public class SleepUtils {
    public static final void seconds(long seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
