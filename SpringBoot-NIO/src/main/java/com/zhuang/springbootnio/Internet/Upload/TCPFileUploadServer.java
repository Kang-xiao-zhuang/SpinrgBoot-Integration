package com.zhuang.springbootnio.Internet.Upload;

import com.zhuang.springbootnio.Internet.StreamUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Classname TCPFileUploadServer
 * @Description 文件上传服务端
 * @Date 2021/6/4 9:25
 * @Created by dell
 */

public class TCPFileUploadServer {
    public static void main(String[] args) throws Exception{
        // 在本机监听8888端口
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("服务端在8888端口监听......");
        //等待连接
        Socket socket = serverSocket.accept();
        //读取客户端的数据 通过Socket得到输入流
        BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
        byte[] bytes = StreamUtils.streamToByteArray(bis);
        //将得到的数组写入指定路径
        String desFilePath = "f:\\2.jpg";
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(desFilePath));
        bos.write(bytes);
        bos.close();

        //向客户端回复 收到图片
        //通过socket 获取到输入流
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        writer.write("收到图片");
        // 把内容刷新到数据通道
        writer.flush();
        //设置写入结束标记
        socket.shutdownOutput();

        socket.close();
        serverSocket.close();
        bos.close();
        bis.close();
        writer.close();
    }
}
