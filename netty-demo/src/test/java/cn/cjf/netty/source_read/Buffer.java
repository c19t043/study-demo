package cn.cjf.netty.source_read;

/**
 * capacity 属性，容量，Buffer 能容纳的数据元素的最大值。这一容量在 Buffer 创建时被赋值，并且永远不能被修改。
 * Buffer 分成写模式和读模式两种情况。
 * position 属性，位置，初始值为 0 。
 * 写模式下，每往 Buffer 中写入一个值，position 就自动加 1 ，代表下一次的写入位置。
 * 读模式下，每从 Buffer 中读取一个值，position 就自动加 1 ，代表下一次的读取位置。( 和写模式类似 )
 * limit 属性，上限。
 * 写模式下，代表最大能写入的数据上限位置，这个时候 limit 等于 capacity 。
 * 读模式下，在 Buffer 完成所有数据写入后，通过调用 #flip() 方法，切换到读模式。此时，limit 等于 Buffer 中实际的数据大小。因为 Buffer 不一定被写满，所以不能使用 capacity 作为实际的数据大小。
 * mark 属性，标记，通过 #mark() 方法，记录当前 position ；通过 reset() 方法，恢复 position 为标记。
 * 写模式下，标记上一次写位置。
 * 读模式下，标记上一次读位置。
 * 四个属性总是遵循如下大小关系：
 * mark <= position <= limit <= capacity
 *
 * 相比较来说，Netty 的 ByteBuf 就优雅的非常多，基本属性设计如下：
 * 0 <= readerIndex <= writerIndex <= capacity
 * 通过 readerIndex 和 writerIndex 两个属性，避免出现读模式和写模式的切换。
 */
public abstract class Buffer {

    // Invariants: mark <= position <= limit <= capacity
    private int mark = -1;
    private int position = 0;
    private int limit;
    private int capacity;

    // Used only by direct buffers
    // NOTE: hoisted here for speed in JNI GetDirectBufferAddress
    long address;

    Buffer(int mark, int pos, int lim, int cap) {       // package-private
        if (cap < 0)
            throw new IllegalArgumentException("Negative capacity: " + cap);
        this.capacity = cap;
//        limit(lim);
//        position(pos);
        if (mark >= 0) {
            if (mark > pos)
                throw new IllegalArgumentException("mark > position: ("
                                                   + mark + " > " + pos + ")");
            this.mark = mark;
        }
    }
    
    // ... 省略具体方法的代码
}