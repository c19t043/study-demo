package cn.cjf.demo1;

import org.junit.Test;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class Demo1 {
    StringBuilder mRxOperatorsText = new StringBuilder();

    /**
     * 这里出现了一个叫做 Func1 的类。它和 Action1 非常相似，也是 RxJava 的一个接口，用于包装含有一个参数的方法。 Func1 和 Action 的区别在于， Func1 包装的是有返回值的方法。另外，和 ActionX 一样， FuncX 也有多个，用于不同参数个数的方法。FuncX 和 ActionX 的区别在 FuncX 包装的是有返回值的方法。
     */
    @Test
    public void test5(){
//        Observable.just("images/logo.png") // 输入类型 String
//                .map(new Func1<String, Bitmap>() {
//                    @Override
//                    public Bitmap call(String filePath) { // 参数类型 String
//                        return getBitmapFromPath(filePath); // 返回类型 Bitmap
//                    }
//                })
//                .subscribe(new Action1<Bitmap>() {
//                    @Override
//                    public void call(Bitmap bitmap) { // 参数类型 Bitmap
//                        showBitmap(bitmap);
//                    }
//                });
    }


    @Test
    public void test4(){
//        int drawableRes = ...;
//        ImageView imageView = ...;
//        Observable.create(new OnSubscribe<Drawable>() {
//            @Override
//            public void call(Subscriber<? super Drawable> subscriber) {
//                Drawable drawable = getTheme().getDrawable(drawableRes));
//                subscriber.onNext(drawable);
//                subscriber.onCompleted();
//            }
//        })
//                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
//                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
//                .subscribe(new Observer<Drawable>() {
//                    @Override
//                    public void onNext(Drawable drawable) {
//                        imageView.setImageDrawable(drawable);
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Toast.makeText(activity, "Error!", Toast.LENGTH_SHORT).show();
//                    }
//                });
    }

    /**
     * 两句 subscribeOn(Scheduler.io()) 和 observeOn(AndroidSchedulers.mainThread()) 的使用方式非常常见，它适用于多数的 『后台线程取数据，主线程显示』的程序策略。
     */
    @Test
    public void test3() {
        Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
//                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .observeOn(Schedulers.newThread())
                .subscribe(new Action1<Integer>() {
                    public void call(Integer number) {
                    }
                });
    }

    /**
     * 由 id 取得图片并显示
     * 由指定的一个 drawable 文件 id drawableRes 取得图片，并显示在 ImageView 中，并在出现异常的时候打印 Toast 报错：
     */
    @Test
    public void test2() {
//        int drawableRes = 0;
//        ImageView imageView = new ImageView();
//        Observable.create(new OnSubscribe<Drawable>() {
//            @Override
//            public void call(Subscriber<? super Drawable> subscriber) {
//                Drawable drawable = getTheme().getDrawable(drawableRes));
//                subscriber.onNext(drawable);
//                subscriber.onCompleted();
//            }
//        }).subscribe(new Observer<Drawable>() {
//            @Override
//            public void onNext(Drawable drawable) {
//                imageView.setImageDrawable(drawable);
//            }
//
//            @Override
//            public void onCompleted() {
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Toast.makeText(activity, "Error!", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    /**
     * 打印字符串数组
     * 将字符串数组 names 中的所有字符串依次打印出来：
     */
    @Test
    public void test1() {

        String[] names = new String[2];
        Observable.from(names)
                .subscribe(new Action1<String>() {
                    public void call(String name) {
                    }
                });
    }


    @Test
    public void test() {
        Observer<String> observer = new Observer<String>() {
            public void onNext(String s) {
            }

            public void onCompleted() {
            }

            public void onError(Throwable e) {
            }
        };

        Subscriber<String> subscriber = new Subscriber<String>() {
            public void onNext(String s) {
            }

            public void onCompleted() {
            }

            public void onError(Throwable e) {
            }
        };

        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("Hi");
                subscriber.onNext("Aloha");
                subscriber.onCompleted();
            }
        });

        //just(T...): 将传入的参数依次发送出来。
        Observable observable1 = Observable.just("Hello", "Hi", "Aloha");
        // 将会依次调用：
// onNext("Hello");
// onNext("Hi");
// onNext("Aloha");
// onCompleted();


        //from(T[]) / from(Iterable<? extends T>) : 将传入的数组或 Iterable 拆分成具体对象后，依次发送出来。
        String[] words = {"Hello", "Hi", "Aloha"};
        Observable observable2 = Observable.from(words);
// 将会依次调用：
// onNext("Hello");
// onNext("Hi");
// onNext("Aloha");
// onCompleted();

        //创建了 Observable 和 Observer 之后，再用 subscribe() 方法将它们联结起来，整条链子就可以工作了。代码形式很简单：
        observable.subscribe(observer);
// 或者：
        observable.subscribe(subscriber);


        Action1<String> onNextAction = new Action1<String>() {
            // onNext()
            public void call(String s) {
            }
        };
        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            // onError()
            public void call(Throwable throwable) {
                // Error handling
            }
        };
        Action0 onCompletedAction = new Action0() {
            // onCompleted()
            public void call() {
            }
        };

// 自动创建 Subscriber ，并使用 onNextAction 来定义 onNext()
        observable.subscribe(onNextAction);
// 自动创建 Subscriber ，并使用 onNextAction 和 onErrorAction 来定义 onNext() 和 onError()
        observable.subscribe(onNextAction, onErrorAction);
// 自动创建 Subscriber ，并使用 onNextAction、 onErrorAction 和 onCompletedAction 来定义 onNext()、 onError() 和 onCompleted()
        observable.subscribe(onNextAction, onErrorAction, onCompletedAction);
    }
}
