package com.zhuang.springbootnio.channel;

import lombok.extern.slf4j.Slf4j;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Slf4j
public class FileChannelDemo1 {
    public static void main(String[] args) throws Exception {
        // 创建FileChannel
        RandomAccessFile randomAccessFile = new RandomAccessFile("d:\\01.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();
        // 创建Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 读取数据到buffer中
        int bytesRead = channel.read(buffer);
        while (bytesRead != -1) {
            log.info("读取了：" + bytesRead);
            buffer.flip();
            while (buffer.hasRemaining()) {
                System.out.println((char) buffer.get());
            }
            buffer.clear();
            bytesRead = channel.read(buffer);
        }
        randomAccessFile.close();
        log.info("结束了！！！！！！！！");
    }
}
