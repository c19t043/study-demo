package cn.cjf.nio.buffer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelTest {
    /**
     * 使用FileChannel读取数据到Buffer中的示例：
     * 注意 buf.flip() 的调用，首先读取数据到Buffer，然后反转Buffer,接着再从Buffer中读取数据。
     */
    public static void main(String[] args) {
        try (RandomAccessFile aFile = new RandomAccessFile("data/nio-data.txt", "rw");
             FileChannel inChannel = aFile.getChannel()) {
            ByteBuffer buf = ByteBuffer.allocate(48);

            int bytesRead = inChannel.read(buf);
            while (bytesRead != -1) {

                System.out.println("Read " + bytesRead);
                buf.flip();

                while (buf.hasRemaining()) {
                    System.out.print((char) buf.get());
                }

                buf.clear();
                bytesRead = inChannel.read(buf);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
