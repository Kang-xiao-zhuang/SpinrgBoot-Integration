package com.zhuang.springbootnio.Internet.Tcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Classname SocketTCP03Server
 * @Description 服务端
 * @Date 2021/6/3 12:47
 * @Created by dell
 */

public class SocketTCP03Server {
    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(9999);
        /*
        没有客户端连接9999端口会一直阻塞，等待连接
        如果有客户端连接，会返回Socket对象，程序继续
         */
        System.out.println("服务端在9999端口监听！");

        Socket socket = serverSocket.accept();
        System.out.println("服务端 socket="+socket.getClass());

        //2. 连接上后，生成 Socket, 通过 socket.getOutputStream()
        //3. 通过 socket.getInputStream() 读取客户端写入到数据通道的数据, 显示
        InputStream inputStream = socket.getInputStream();
        //4. IO 读取, 使用字符流, 老师使用 InputStreamReader 将 inputStream 转成字符流
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String s = bufferedReader.readLine();
        System.out.println(s);

        //5. 获取 socket 相关联的输出流
        OutputStream outputStream = socket.getOutputStream();

        // 使用字符输出流的方式回复信息
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        bufferedWriter.write("hello client 字符流");
        bufferedWriter.newLine();// 插入一个换行符，表示回复内容的结束
        bufferedWriter.flush();//注意需要手动的 flush


        bufferedWriter.close();
        bufferedReader.close();
        socket.close();
        serverSocket.close();//关闭
    }
}
