package com.zhuang.springbootnio.asyncfilechannel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousByteChannel;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

public class AsyncFileChannelDemo {

    public static void main(String[] args) throws IOException {
        AsyncFileChannelDemo asyncFileChannelDemo = new AsyncFileChannelDemo();
        //asyncFileChannelDemo.readAsyncFilehannelFuture();
        //asyncFileChannelDemo.readAsyncFilehannelComplate();
        //asyncFileChannelDemo.writeAsyncFilehannelFuture();
        //asyncFileChannelDemo.writeAsyncFilehannelComplate();
    }

    /**
     * 异步读数据
     */
    public void readAsyncFilehannelFuture() throws IOException {
        //创建AsynchronousFileChannel
        Path path = Paths.get("d:\\01.txt");
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
        // 创建Buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // 调用channel的read方法得到Future
        Future<Integer> future = fileChannel.read(byteBuffer, 0);
        // 判断是否完成isDone，返回true
        while (!future.isDone()) ;
        // 读取数据到buffer里面
        byteBuffer.flip();
        /*
        while (byteBuffer.remaining() > 0) {
            System.out.println(byteBuffer.get());
        }*/
        byte[] bytes = new byte[byteBuffer.limit()];
        byteBuffer.get(bytes);
        System.out.println(new String(bytes));
        byteBuffer.clear();
    }

    /**
     * 异步读数据
     */
    public void readAsyncFilehannelComplate() throws IOException {
        //创建AsynchronousFileChannel
        Path path = Paths.get("d:\\01.txt");
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
        // 创建Buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // 调用channel的read方法得到Future
        fileChannel.read(byteBuffer, 0, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer integer, ByteBuffer byteBuffer) {
                System.out.println(integer);
                // 读取数据到buffer里面
                byteBuffer.flip();
                /*
                while (byteBuffer.remaining() > 0) {
                    System.out.println(byteBuffer.get());
                }*/
                byte[] bytes = new byte[byteBuffer.limit()];
                byteBuffer.get(bytes);
                System.out.println(new String(bytes));
                byteBuffer.clear();
            }

            @Override
            public void failed(Throwable throwable, ByteBuffer byteBuffer) {

            }
        });
    }

    /**
     * 异步写数据
     */
    public void writeAsyncFilehannelFuture() throws IOException {
        //创建AsynchronousFileChannel
        Path path = Paths.get("d:\\01.txt");
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);
        // 创建Buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // 调用channel的read方法得到Future
        byteBuffer.put("zk哈哈哈".getBytes(StandardCharsets.UTF_8));
        byteBuffer.flip();
        Future<Integer> future = fileChannel.write(byteBuffer, 0);
        while (!future.isDone()) ;
        System.out.println("write over");
    }

    /**
     * 异步写数据
     */
    public void writeAsyncFilehannelComplate() throws IOException {
        //创建AsynchronousFileChannel
        Path path = Paths.get("d:\\01.txt");
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
        // 创建Buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // write方法
        byteBuffer.put("zkhhh".getBytes(StandardCharsets.UTF_8));
        byteBuffer.flip();
        fileChannel.write(byteBuffer, 0, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer integer, ByteBuffer byteBuffer) {
                System.out.println(integer);
            }

            @Override
            public void failed(Throwable throwable, ByteBuffer byteBuffer) {
                System.out.println(throwable);
            }
        });
    }
}
