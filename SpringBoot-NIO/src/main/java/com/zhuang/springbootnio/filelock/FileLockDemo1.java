package com.zhuang.springbootnio.filelock;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileLockDemo1 {
    public static void main(String[] args) throws Exception {
        String input = "hello zk";
        System.out.println("input = " + input);
        ByteBuffer byteBuffer = ByteBuffer.wrap(input.getBytes(StandardCharsets.UTF_8));
        String filePath = "d:\\01.txt";
        Path path = Paths.get(filePath);
        FileChannel channel = FileChannel.open(path, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
        channel.position(channel.size() - 1);
        //加锁
        FileLock lock = channel.lock(0L, Long.MAX_VALUE, true);
        System.out.println("是否为共享锁 = " + lock.isShared());

        channel.write(byteBuffer);
        channel.close();

        // 读文件
        readFile(filePath);
    }

    private static void readFile(String filePath) throws Exception {
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String str = bufferedReader.readLine();
        System.out.println("str = " + str);
        while (str != null) {
            System.out.println("str = " + str);
            str = bufferedReader.readLine();
        }
        bufferedReader.close();
        fileReader.close();
    }
}
