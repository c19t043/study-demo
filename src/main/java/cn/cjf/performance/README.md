# 性能
## 1.利用软引用和弱引用，有效避免OOM
+ OOM:OutOfMemory
+ 四种类型的引用：强引用（StrongReference）、软引用（SoftReference）、
弱引用（WeakReference）、虚引用（PhantomReference）
### 1.1 强引用
与对象有直接关联的引用
> Object object = new Object();
> object 为强引用
### 1.2 软引用
使用java.lang.ref.SoftReference关联的对象
> `SoftReference<String> ref = new SoftReference<String>("123");`
+ 当内存不足时，垃圾回收机制将回收软引用使用的内存
### 1.3 弱引用
使用java.lang.ref.WeakReference关联的对象
> `WeakReference<String> ref = new WeakRefence<String>("123");`
+ 当执行垃圾回收时，将被回收
### 1.4 虚引用
使用java.lang.ref.PhantomReference关联的对象
必须关联java.lang.ref.ReferenceQueue使用
> `        ReferenceQueue<String> queue = new ReferenceQueue<String>();`
> `PhantomReference<String> ref2 = new PhantomReference<>("123",queue);`
+ 当执行垃圾回收时，将被回收
### 1.5 示例
+ 使用软引用构建敏感数据的缓存
~~~JAVA
// 获取对象并缓存
Object object = new Object();
SoftReference softRef = new SoftReference(object);

// 从软引用中获取对象
Object object = (Object) softRef.get();
if (object == null){
    // 当软引用被回收后重新获取对象
    object = new Object();
}
~~~
