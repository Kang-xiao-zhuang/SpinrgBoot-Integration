package com.zhuang.springbootnio.buffer;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

public class BufferDemo1 {

    public static void main(String[] args) throws Exception {
        BufferDemo1 bufferDemo1 = new BufferDemo1();
//        bufferDemo1.buffer01();
        bufferDemo1.buffer02();
    }

    public void buffer01() throws Exception {
        // 创建FileChannel
        RandomAccessFile randomAccessFile = new RandomAccessFile("d:\\01.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();
        //创建buffer 大小
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        int byteRead = channel.read(buffer);
        while (byteRead != -1) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                System.out.print((char) buffer.get()); // read 1 byte at a time
            }
            buffer.clear();
            byteRead = channel.read(buffer);
        }
        randomAccessFile.close();
    }


    public void buffer02() throws Exception {
        // 创建buffer
        IntBuffer buffer = IntBuffer.allocate(8);
        for (int i = 0; i < buffer.capacity(); i++) {
            int j = 2 * (i + 1);
            buffer.put(j);
        }
        buffer.flip();
        //获取
        while (buffer.hasRemaining()) {
            int value = buffer.get();
            System.out.println(value + " ");
        }
    }
}
