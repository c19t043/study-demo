package cn.cjf.netty.source_read;

public abstract class ByteBuffer{
    /**
     * 每个 Buffer 实现类，都提供了 #allocate(int capacity) 静态方法，
     * 帮助我们快速实例化一个 Buffer 对象。以 ByteBuffer 举例子
     *
     * HeapByteBuffer 基于堆内( Non-Direct )内存的实现类
     *
     * ByteBuffer 实际是个抽象类，返回的是它的基于堆内( Non-Direct )内存的实现类 HeapByteBuffer 的对象。
     */
//    public static ByteBuffer allocate(int capacity) {
//        if (capacity < 0)
//            throw new IllegalArgumentException();
//        return new HeapByteBuffer(capacity, capacity);
//    }

    /**
     *  每个 Buffer 实现类，都提供了 #wrap(array) 静态方法，帮助我们将其对应的数组包装成一个 Buffer 对象。
     */

//    public static ByteBuffer wrap(byte[] array, int offset, int length){
//        try {
//            return new HeapByteBuffer(array, offset, length);
//        } catch (IllegalArgumentException x) {
//            throw new IndexOutOfBoundsException();
//        }
//    }
//
//    public static ByteBuffer wrap(byte[] array) {
//        return wrap(array, 0, array.length);
//    }

    /**
     * 每个 Buffer 实现类，都提供了 #allocateDirect(int capacity) 静态方法，帮助我们快速实例化一个 Buffer 对象
     *
     * 和 #allocate(int capacity) 静态方法不一样，返回的是它的基于堆外( Direct )内存的实现类 DirectByteBuffer 的对象。
     */
//    public static ByteBuffer allocateDirect(int capacity) {
//        return new DirectByteBuffer(capacity);
//    }

    /**
     * 每个 Buffer 实现类，都提供了 #put(...) 方法，向 Buffer 写入数据。
     */
    // 写入 byte
//    public abstract ByteBuffer put(byte b);
//    public abstract ByteBuffer put(int index, byte b);
//    // 写入 byte 数组
//    public final ByteBuffer put(byte[] src) { ... }
//    public ByteBuffer put(byte[] src, int offset, int length) {...}
// ... 省略，还有其他 put 方法

    /**
     * 对于 Buffer 来说，有一个非常重要的操作就是，我们要讲来自 Channel 的数据写入到 Buffer 中。在系统层面上，这个操作我们称为读操作，因为数据是从外部( 文件或者网络等 )读取到内存中。
     *
     * 上述方法会返回从 Channel 中写入到 Buffer 的数据大小
     *
     * 注意，通常在说 NIO 的读操作的时候，我们说的是从 Channel 中读数据到 Buffer 中，对应的是对 Buffer 的写入操作，
     */
//    int num = channel.read(buffer);

//    public interface ReadableByteChannel extends Channel {
//
//        public int read(ByteBuffer dst) throws IOException;
//
//    }


    /**
     * 每个 Buffer 实现类，都提供了 #get(...) 方法，从 Buffer 读取数据。
     */
//    // 读取 byte
//    public abstract byte get();
//    public abstract byte get(int index);
//    // 读取 byte 数组
//    public ByteBuffer get(byte[] dst, int offset, int length) {...}
//    public ByteBuffer get(byte[] dst) {...}
//// ... 省略，还有其他 get 方法
    /**
     * 对于 Buffer 来说，还有一个非常重要的操作就是，我们要将向 Channel 的写入 Buffer 中的数据。在系统层面上，这个操作我们称为写操作，因为数据是从内存中写入到外部( 文件或者网络等 )。
     *
     * 上述方法会返回向 Channel 中写入 Buffer 的数据大小
     */
//    int num = channel.write(buffer);

//    public interface WritableByteChannel extends Channel {
//
//        public int write(ByteBuffer src) throws IOException;
//
//    }

    /**
     * 如果要读取 Buffer 中的数据，需要切换模式，从写模式切换到读模式。对应的为 #flip() 方法
     *
     */
//    public final Buffer flip() {
//        limit = position; // 设置读取上限
//        position = 0; // 重置 position
//        mark = -1; // 清空 mark
//        return this;
//    }
    /**
     * 使用示例，
     */
//buf.put(magic);    // Prepend header
//in.read(buf);      // Read data into rest of buffer
//buf.flip();        // Flip buffer
//channel.write(buf);    // Write header + data to channel

    /**
     * #rewind() 方法，可以重置 position 的值为 0 。因此，我们可以重新读取和写入 Buffer 了。
     * 大多数情况下，该方法主要针对于读模式，所以可以翻译为“倒带”。也就是说，和我们当年的磁带倒回去是一个意思。
     *
     * 从代码上，和 #flip() 相比，非常类似，除了少了第一行的 limit = position 的代码块。
     */
//    public final Buffer rewind() {
//        position = 0; // 重置 position
//        mark = -1; // 清空 mark
//        return this;
//    }
    /**
     * 使用示例，
     */
//channel.write(buf);    // Write remaining data
//buf.rewind();      // Rewind buffer
//buf.get(array);    // Copy data into array

    /**
     * #clear() 方法，可以“重置” Buffer 的数据。因此，我们可以重新读取和写入 Buffer 了。
     *
     * 大多数情况下，该方法主要针对于写模式。
     *
     * 从源码上，我们可以看出，Buffer 的数据实际并未清理掉，所以使用时需要注意
     * 读模式下，尽量不要调用 #clear() 方法，因为 limit 可能会被错误的赋值为 capacity 。相比来说，调用 #rewind() 更合理，如果有重读的需求。
     *
     */
//    public final Buffer clear() {
//        position = 0; // 重置 position
//        limit = capacity; // 恢复 limit 为 capacity
//        mark = -1; // 清空 mark
//        return this;
//    }
    /**
     * 使用示例
     */
//buf.clear();     // Prepare buffer for reading
//in.read(buf);    // Read data

    /**
     * #mark() 方法，保存当前的 position 到 mark 中。
     */
//    public final Buffer mark() {
//        mark = position;
//        return this;
//    }
    /**
     * #reset() 方法，恢复当前的 postion 为 mark 。
     */
//    public final Buffer reset() {
//        int m = mark;
//        if (m < 0)
//            throw new InvalidMarkException();
//        position = m;
//        return this;
//    }

//    // ========== capacity ==========
//    public final int capacity() {
//        return capacity;
//    }
//
//    // ========== position ==========
//    public final int position() {
//        return position;
//    }
//
//    public final Buffer position(int newPosition) {
//        if ((newPosition > limit) || (newPosition < 0))
//            throw new IllegalArgumentException();
//        position = newPosition;
//        if (mark > position) mark = -1;
//        return this;
//    }
//
//    // ========== limit ==========
//    public final int limit() {
//        return limit;
//    }
//
//    public final Buffer limit(int newLimit) {
//        if ((newLimit > capacity) || (newLimit < 0))
//            throw new IllegalArgumentException();
//        limit = newLimit;
//        if (position > limit) position = limit;
//        if (mark > limit) mark = -1;
//        return this;
//    }
//
//    // ========== mark ==========
//    final int markValue() {                             // package-private
//        return mark;
//    }
//
//    final void discardMark() {                          // package-private
//        mark = -1;
//    }
//
//    // ========== 数组相关 ==========
//    public final int remaining() {
//        return limit - position;
//    }
//
//    public final boolean hasRemaining() {
//        return position < limit;
//    }
//
//    public abstract boolean hasArray();
//
//    public abstract Object array();
//
//    public abstract int arrayOffset();
//
//    public abstract boolean isDirect();
//
//    // ========== 下一个读 / 写 position ==========
//    final int nextGetIndex() {                          // package-private
//        if (position >= limit)
//            throw new BufferUnderflowException();
//        return position++;
//    }
//
//    final int nextGetIndex(int nb) {                    // package-private
//        if (limit - position < nb)
//            throw new BufferUnderflowException();
//        int p = position;
//        position += nb;
//        return p;
//    }
//
//    final int nextPutIndex() {                          // package-private
//        if (position >= limit)
//            throw new BufferOverflowException();
//        return position++;
//    }
//
//    final int nextPutIndex(int nb) {                    // package-private
//        if (limit - position < nb)
//            throw new BufferOverflowException();
//        int p = position;
//        position += nb;
//        return p;
//    }
//
//    final int checkIndex(int i) {                       // package-private
//        if ((i < 0) || (i >= limit))
//            throw new IndexOutOfBoundsException();
//        return i;
//    }
//
//    final int checkIndex(int i, int nb) {               // package-private
//        if ((i < 0) || (nb > limit - i))
//            throw new IndexOutOfBoundsException();
//        return i;
//    }
//
//    // ========== 其它方法 ==========
//    final void truncate() {                             // package-private
//        mark = -1;
//        position = 0;
//        limit = 0;
//        capacity = 0;
//    }
//
//    static void checkBounds(int off, int len, int size) { // package-private
//        if ((off | len | (off + len) | (size - (off + len))) < 0)
//            throw new IndexOutOfBoundsException();
//    }
}
