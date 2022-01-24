package com.zhuang.springbootnio.Internet.HomeWork;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @Classname Homework02ReceiverA
 * @Description UDP接收端
 * @Date 2021/6/4 10:59
 * @Created by dell
 */

public class Homework02ReceiverA {
    public static void main(String[] args) throws IOException {
        //1. 创建一个 DatagramSocket 对象，准备在8888接收数据
        DatagramSocket socket = new DatagramSocket(8888);
        //2. 构建一个 DatagramPacket 对象，准备接收数据
        byte[] buf = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        //3. 调用 接收方法, 将通过网络传输的 DatagramPacket 对象
        //   填充到 packet对象
        System.out.println("接收端 等待接收问题 ");
        socket.receive(packet);

        //4. 可以把packet 进行拆包，取出数据，并显示.
        //实际接收到的数据字节长度
        int length = packet.getLength();
        //接收到数据
        byte[] data = packet.getData();
        String s = new String(data, 0, length);
        //判断接收到的信息是什么
        String answer = "";
        if("四大名著是哪些".equals(s)) {
            answer = "四大名著 <<红楼梦>> <<三国演示>> <<西游记>> <<水浒传>>";
        } else {
            answer = "what?";
        }


        //===回复信息给B端
        //将需要发送的数据，封装到 DatagramPacket对象
        data = answer.getBytes();
        //说明: 封装的 DatagramPacket对象 data 内容字节数组 , data.length , 主机(IP) , 端口
        packet =
                new DatagramPacket(data, data.length, InetAddress.getLocalHost(), 9998);

        socket.send(packet);

        //5. 关闭资源
        socket.close();
        System.out.println("A端退出...");

    }
}
