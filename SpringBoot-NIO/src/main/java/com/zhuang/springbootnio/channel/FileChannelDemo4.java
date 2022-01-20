package com.zhuang.springbootnio.channel;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class FileChannelDemo4 {
    //通道之间数据传输
    public static void main(String[] args) throws Exception {
        // transferForm
        // 创建两个FileChannel
        RandomAccessFile aFile = new RandomAccessFile("d:\\01.txt", "rw");
        FileChannel fromChannel = aFile.getChannel();

        RandomAccessFile bFile = new RandomAccessFile("d:\\03.txt", "rw");
        FileChannel toChannel = bFile.getChannel();

        // fromChannel 传输到 toChannel
        long size = fromChannel.size();
        fromChannel.transferTo(1, size, toChannel);

        aFile.close();
        bFile.close();
        System.out.println("over!");
    }
}
