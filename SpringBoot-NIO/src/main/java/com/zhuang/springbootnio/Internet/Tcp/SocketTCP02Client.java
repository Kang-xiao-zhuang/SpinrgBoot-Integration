package com.zhuang.springbootnio.Internet.Tcp;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @Classname SocketTCP02Client
 * @Description 客户端
 * @Date 2021/6/3 12:21
 * @Created by dell
 */

public class SocketTCP02Client {
    public static void main(String[] args) throws Exception{
        //思路
    //1. 连接服务端 (ip , 端口）
    //解读: 连接本机的 9999 端口, 如果连接成功，返回 Socket 对象
        Socket socket = new Socket(InetAddress.getLocalHost(), 9999);
        System.out.println("客户端 socket 返回=" + socket.getClass());
    //2. 连接上后，生成 Socket, 通过 socket.getOutputStream()
    // 得到 和 socket 对象关联的输出流对象
        OutputStream outputStream = socket.getOutputStream();
    //3. 通过输出流，写入数据到 数据通道
        outputStream.write("hello, server".getBytes());
        // 设置结束标记
        socket.shutdownOutput();
    //4. 获取和 socket 关联的输入流. 读取数据(字节)，并显示
        InputStream inputStream = socket.getInputStream();
        byte[] buf = new byte[1024];
        int readLen = 0;
        while ((readLen = inputStream.read(buf)) != -1) {
            System.out.println(new String(buf, 0, readLen));
        }
    //5. 关闭流对象和 socket, 必须关闭
        outputStream.close();
        inputStream.close();
        socket.close();
        System.out.println("客户端退出.....");
    }
}
