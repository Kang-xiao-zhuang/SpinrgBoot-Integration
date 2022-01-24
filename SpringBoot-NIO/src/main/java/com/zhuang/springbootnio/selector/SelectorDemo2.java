package com.zhuang.springbootnio.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class SelectorDemo2 {

    public static void main(String[] args) throws Exception {
        SelectorDemo2 selectorDemo2 = new SelectorDemo2();
        //selectorDemo2.clientDemo();
        //selectorDemo2.serverDemo();
        selectorDemo2.scannerDemo();
    }

    public void clientDemo() throws IOException {
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
        byteBuffer.clear();
    }

    public void serverDemo() throws IOException {
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

    public void scannerDemo() throws IOException {
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
            byteBuffer.clear();
        }
    }
}
