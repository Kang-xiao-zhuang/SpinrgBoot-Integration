package com.zhuang.springbootnio.Internet.HomeWork;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * @Classname Homework02SenderB
 * @Description 发送端B ====> 也可以接收数据
 * @Date 2021/6/4 10:59
 * @Created by dell
 */

public class Homework02SenderB {
    public static void main(String[] args) throws IOException {

        //1.创建 DatagramSocket 对象，准备在9998端口 接收数据
        DatagramSocket socket = new DatagramSocket(9998);

        //2. 将需要发送的数据，封装到 DatagramPacket对象
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入你的问题: ");
        String question = scanner.next();
        byte[] data = question.getBytes();

        //说明: 封装的 DatagramPacket对象 data 内容字节数组 , data.length , 主机(IP) , 端口
        DatagramPacket packet =
                new DatagramPacket(data, data.length, InetAddress.getLocalHost(), 8888);

        socket.send(packet);

        //3.=== 接收从A端回复的信息
        //(1)   构建一个 DatagramPacket 对象，准备接收数据
        byte[] buf = new byte[1024];
        packet = new DatagramPacket(buf, buf.length);
        //(2)    调用 接收方法, 将通过网络传输的 DatagramPacket 对象
        //   填充到 packet对象
        socket.receive(packet);

        //(3)  可以把packet 进行拆包，取出数据，并显示.
        //实际接收到的数据字节长度
        int length = packet.getLength();
        //接收到数据
        data = packet.getData();
        String s = new String(data, 0, length);
        System.out.println(s);

        //关闭资源
        socket.close();
        System.out.println("B端退出");
    }
}
