package com.zhuang.springbootnio.channel;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class FileChannelDemo2 {
    public static void main(String[] args) throws Exception {
        //FileChannel写操作
        // 打开FielChannel
        RandomAccessFile randomAccessFile = new RandomAccessFile("d:\\01.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();

        // 创建Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.clear();
        String data = "data 庄康";
        buffer.clear();

        //写入数据
        buffer.put(data.getBytes(StandardCharsets.UTF_8));
        buffer.flip();

        // FileChannel最终完成实现
        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
        // 关闭
        channel.close();
    }
}
