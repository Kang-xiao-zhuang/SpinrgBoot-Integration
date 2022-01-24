package com.zhuang.springbootnio.Internet.Tcp;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Classname SocketTCP01Server
 * @Description 服务端
 * @Date 2021/6/3 12:20
 * @Created by dell
 */

public class SocketTCP01Server {
    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(9999);
        /*
        没有客户端连接9999端口会一直阻塞，等待连接
        如果有客户端连接，会返回Socket对象，程序继续
         */
        System.out.println("服务端在9999端口监听！");


        Socket socket = serverSocket.accept();
        System.out.println("服务端 socket="+socket.getClass());

        //通过socket.getInputStream()  读取客户端写入数据通信的数据，显示
        InputStream inputStream = socket.getInputStream();

        // IO读取
        byte[] buf=new byte[1024];
        int readLength = 0;
        while ((readLength=inputStream.read(buf))!=-1){
            //读取到的实际长度，显示内容
            System.out.println(new String(buf,0, readLength));
        }
//        关闭流和socket
        inputStream.close();
        socket.close();
        serverSocket.close();
    }
}
