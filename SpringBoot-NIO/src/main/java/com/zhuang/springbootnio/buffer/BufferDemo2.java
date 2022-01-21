package com.zhuang.springbootnio.buffer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class BufferDemo2 {
    public static void main(String[] args) throws IOException {
        BufferDemo2 bufferDemo2 = new BufferDemo2();
        //  bufferDemo2.buffer01();
        // bufferDemo2.buffer02();
        bufferDemo2.buffer03();
    }

    /**
     * 间接缓冲区
     */
    public void buffer01() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }
        // 创建子缓冲区
        buffer.position(3);
        buffer.limit(7);
        ByteBuffer slice = buffer.slice();

        //改变子缓冲区内容
        for (int i = 0; i < slice.capacity(); i++) {
            byte b = slice.get(i);
            b *= 10;
            slice.put(i, b);
        }
        buffer.position(0);
        buffer.limit(buffer.capacity());
        while (buffer.remaining() > 0) {
            System.out.println(buffer.get());
        }
    }

    /**
     * 只读缓冲区
     */
    public void buffer02() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }
        // 创建只读缓冲区
        ByteBuffer readonly = buffer.asReadOnlyBuffer();
        for (int i = 0; i < buffer.capacity(); i++) {
            byte b = buffer.get(i);
            b *= 10;
            buffer.put(i, b);
        }
        readonly.position(0);
        readonly.limit(buffer.capacity());
        while (readonly.remaining() > 0) {
            System.out.println(readonly.get());
        }
    }


    /**
     * 直接缓冲区
     */
    public void buffer03() throws IOException {
        String infile = "d:\\01.txt";
        FileInputStream fis = new FileInputStream(infile);
        FileChannel fisChannel = fis.getChannel();
        String outfile = "d:\\02.txt";
        FileOutputStream fos = new FileOutputStream(outfile);
        FileChannel fosChannel = fos.getChannel();
        // 创建直接缓冲区
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        while (true) {
            buffer.clear();
            int read = fisChannel.read(buffer);
            if (read == -1) {
                break;
            }
            buffer.flip();
            fosChannel.write(buffer);
        }
    }

    static private final int start = 0;
    static private final int size = 1024;

    /**
     * 内存映射文件
     */
    public void buffer04() throws IOException {

        RandomAccessFile raf = new RandomAccessFile("d:\\01.txt", "rw");
        FileChannel channel = raf.getChannel();
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, start, size);
        map.put(0, (byte) 97);
        map.put(1023, (byte) 122);
        raf.close();

    }
}
