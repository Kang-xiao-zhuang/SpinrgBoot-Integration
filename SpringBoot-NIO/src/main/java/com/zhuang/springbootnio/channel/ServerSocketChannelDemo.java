package com.zhuang.springbootnio.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class ServerSocketChannelDemo {

    public static void main(String[] args) throws IOException, InterruptedException {
        // 端口号
        int port = 8888;

        // buffer
        ByteBuffer buffer = ByteBuffer.wrap("hello zk".getBytes(StandardCharsets.UTF_8));

        //  ServerSocketChannel
        ServerSocketChannel ssc = ServerSocketChannel.open();
        // 绑定
        ssc.socket().bind(new InetSocketAddress(port));

        //设置非阻塞模式
        ssc.configureBlocking(false);

        while (true) {
            System.out.println("WAITING FOR CONNECTION");
            SocketChannel sc = ssc.accept();
            if (sc == null) {
                System.out.println("NULL");
                Thread.sleep(2000);
            } else {
                System.out.println("INCOMING CONNECTION FROM ：" + sc.socket().getRemoteSocketAddress());
                buffer.rewind();
                sc.write(buffer);
                sc.close();
            }
        }
    }
}
