package com.zhuang.springbootnio.Internet.Upload;

import com.zhuang.springbootnio.Internet.StreamUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @Classname TCPFileUploadClient
 * @Description 文件上传客户端
 * @Date 2021/6/4 9:26
 * @Created by dell
 */

public class TCPFileUploadClient {
    public static void main(String[] args) throws Exception{
        //客户端连接服务端8888，得到Socket对象
        Socket socket = new Socket(InetAddress.getLocalHost(), 8888);
        //创建读取磁盘的文件
        String filePath="f:\\1.jpg";
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath));
        //数组就是FilePath所对应的字节数组
        byte[] bytes = StreamUtils.streamToByteArray(bis);

//        通过socket获取到输入流，将bytes数据发送给服务端
        BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
        bos.write(bytes);
        bis.close();
        socket.shutdownOutput();

        /*
        接受从服务端回复的消息

         */
        InputStream inputStream = socket.getInputStream();
        //转成字符串
        String s = StreamUtils.streamToString(inputStream);
        System.out.println(s);


        inputStream.close();
        bos.close();
        socket.close();

    }
}
