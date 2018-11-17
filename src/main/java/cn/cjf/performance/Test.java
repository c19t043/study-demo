package cn.cjf.performance;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

import static java.lang.Thread.sleep;

public class Test {
    @org.junit.Test
    public void test1() throws InterruptedException {
        ReferenceQueue<String> queue = new ReferenceQueue<String>();
        SoftReference<String> ref = new SoftReference<String>("123", queue);
        System.out.println(ref.get());
        System.gc();
        System.out.println(ref.get());
        ref = (SoftReference<String>) queue.poll();
        if (ref != null) {
            System.out.println(ref.get());
        }

//        ReferenceQueue<String> queue = new ReferenceQueue<String>();
        PhantomReference<String> ref2 = new PhantomReference<>("123",queue);
    }
}
