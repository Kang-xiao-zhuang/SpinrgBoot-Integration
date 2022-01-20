package com.zhuang.springbootnio;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@SpringBootTest
class SpringBootNioApplicationTests {

    @Test
    void contextLoads() {
    }


    @Test
    public void sendDatagram() throws Exception {
        DatagramChannel sendChannel = DatagramChannel.open();
        InetSocketAddress sendAdderss = new InetSocketAddress("127.0.0.1", 9999);
        // 发送
        while (true) {
            ByteBuffer buffer = ByteBuffer.wrap("发送hello".getBytes(StandardCharsets.UTF_8));
            sendChannel.send(buffer, sendAdderss);
            System.out.println("已经完成发送");
            Thread.sleep(1000);
        }
    }

    @Test
    public void receiveDatagram() throws Exception {
        DatagramChannel receiveChannel = DatagramChannel.open();
        InetSocketAddress receiveAdderss = new InetSocketAddress(9999);
        // 发送
        receiveChannel.bind(receiveAdderss);

        // buffer
        ByteBuffer receiveBuffer = ByteBuffer.allocate(1024);

        //接收
        while (true) {
            SocketAddress socketAddress = receiveChannel.receive(receiveBuffer);
            receiveBuffer.flip();
            System.out.println(socketAddress.toString());
            System.out.println(Charset.forName("UTF-8").decode(receiveBuffer));
        }
    }

    @Test
    public void testConnect() throws IOException {
        DatagramChannel connChannel = DatagramChannel.open();
        // 绑定
        connChannel.bind(new InetSocketAddress(9999));
        connChannel.connect(new InetSocketAddress("127.0.0.1", 9999));
        // wriete方法
        connChannel.write(ByteBuffer.wrap("发送hello".getBytes(StandardCharsets.UTF_8)));
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        while (true) {
            readBuffer.clear();
            connChannel.read(readBuffer);
            readBuffer.flip();
            System.out.println(Charset.forName("UTF-8").decode(readBuffer));
        }
    }
}
