package com.zhuang.springbootnio;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

@SpringBootTest
class SpringBootNioApplicationTests {

    @Test
    void contextLoads() {
    }


    @Test
    void sendDatagram() throws Exception {
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
    void receiveDatagram() throws Exception {
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
    void testConnect() throws IOException {
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

    @Test
    void clientDemo() throws IOException {
        // 获取通道 绑定主机和端口号
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8888));
        // 切换到非阻塞模式
        socketChannel.configureBlocking(false);
        // 创建buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // 写入buffer数据
        byteBuffer.put(new Date().toString().getBytes(StandardCharsets.UTF_8));
        // 模式切换
        byteBuffer.flip();
        // 写入通道
        socketChannel.write(byteBuffer);
        // 关闭
        socketChannel.close();
    }

    @Test
    void serverDemo() throws IOException {
        // 获取通道 绑定主机和端口号
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 切换到非阻塞模式
        serverSocketChannel.configureBlocking(false);
        // 创建buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // 绑定端口号
        serverSocketChannel.bind(new InetSocketAddress(8888));
        //获取selector选择器
        Selector selector = Selector.open();
        // 通道注册到选择器进行监听
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        // 选择器进行轮询，进行后续操作
        while (selector.select() > 0) {
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();
            while (selectionKeyIterator.hasNext()) {
                SelectionKey key = selectionKeyIterator.next();
                if (key.isAcceptable()) {
                    // 获取连接
                    SocketChannel accept = serverSocketChannel.accept();
                    // 切换非阻塞模式
                    accept.configureBlocking(false);
                    // 注册
                    accept.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    //读取数据
                    int len = 0;
                    while ((len = channel.read(buffer)) > 0) {
                        buffer.flip();
                        System.out.println(new String(buffer.array(), 0, len));
                    }
                }
            }
            // 关闭
            selectionKeyIterator.remove();
        }
    }

    @Test
    void scannerDemo() throws IOException {
        // 获取通道 绑定主机和端口号
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8888));
        // 切换到非阻塞模式
        socketChannel.configureBlocking(false);
        // 创建buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // 写入buffer数据
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String str = scanner.next();
            byteBuffer.put((new Date().toString() + "-->" + str).getBytes(StandardCharsets.UTF_8));
            // 模式切换
            byteBuffer.flip();
            // 写入通道
            socketChannel.write(byteBuffer);
            // 关闭
            socketChannel.close();
        }
    }
}
