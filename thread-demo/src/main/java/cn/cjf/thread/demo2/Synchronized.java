package cn.cjf.thread.demo2;

public class Synchronized {
    public static void main(String[] args) {
        //对Synchronized class对象进行加锁
        synchronized (Synchronized.class){

        }
        //静态同步方法，对Synchronized class对象进行加锁
        m();
    }
    public static synchronized void m(){}
}
/**
 * javap -v Synchronized.class
 *
 *
 public static void main(java.lang.String[]);
 descriptor: ([Ljava/lang/String;)V
 flags: ACC_PUBLIC, ACC_STATIC          //方法修饰符，标识：public static
 Code:
 stack=2, locals=3, args_size=1
 0: ldc           #2                  // class cn/cjf/thread/demo2/Synch                                                                                                                                                                                               ronized
 2: dup
 3: astore_1
 4: monitorenter        //monitorenter：监视器进入，获取锁
 5: aload_1
 6: monitorexit         //monitorexit:监视器退出，释放锁
 7: goto          15
 10: astore_2
 11: aload_1
 12: monitorexit
 13: aload_2
 14: athrow
 15: invokestatic  #3                  // Method m:()V
 18: return
 Exception table:
 from    to  target type
 5     7    10   any
 10    13    10   any
 LineNumberTable:
 line 6: 0
 line 8: 5
 line 10: 15
 line 11: 18
 LocalVariableTable:
 Start  Length  Slot  Name   Signature
 0      19     0  args   [Ljava/lang/String;
 StackMapTable: number_of_entries = 2
 frame_type = 255 // full_frame
          offset_delta = 10
                  locals = [ class "[Ljava/lang/String;", class java/lang/Object ]
        stack = [ class java/lang/Throwable ]
        frame_type = 250 // chop
        offset_delta = 4

public static synchronized void m();
        descriptor: ()V
        flags: ACC_PUBLIC, ACC_STATIC, ACC_SYNCHRONIZED     //方法修饰符，表示：public static synchronized
        Code:
        stack=0, locals=0, args_size=0
        0: return
        LineNumberTable:
        line 12: 0
        }


        *
 */

/**
 *
 * Monitor.Enter    ->  监视器Monitor  --Monitor.Enter成功--> 对象Object --Monitor.Exit--->
 *                   A                  |
 *                   |                  |
 *     Monitor.Exit后通知，出队列      Monitor.Enter失败
 *                   |                  |
 *                   |                  V
 *同步队列SynchronizedQueue(Blocked)     <--
 */
