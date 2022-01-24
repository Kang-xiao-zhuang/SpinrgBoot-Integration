package com.zhuang.springbootnio.pipe;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.charset.StandardCharsets;

public class PipeDemo {
    public static void main(String[] args) throws IOException {
        // 获取管道
        Pipe pipe = Pipe.open();
        // 获取sink通道
        Pipe.SinkChannel sinkChannel = pipe.sink();
        //创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put("hellozk".getBytes(StandardCharsets.UTF_8));
        byteBuffer.flip();
        // 写入数据
        sinkChannel.write(byteBuffer);
        // 获取source通道
        Pipe.SourceChannel sourceChannel = pipe.source();
        // 创建缓冲区，读取数据
        byteBuffer.flip();
        int len = sourceChannel.read(byteBuffer);
        System.out.println(new String(byteBuffer.array(), 0, len));
        // 关闭通道
        sourceChannel.close();
        sinkChannel.close();
    }
}
