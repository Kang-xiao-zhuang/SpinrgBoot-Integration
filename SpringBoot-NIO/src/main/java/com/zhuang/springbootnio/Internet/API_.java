package com.zhuang.springbootnio.Internet;

import java.net.InetAddress;

/**
 * @Classname API_
 * @Description 网络编程常用api
 * @Date 2021/6/3 12:02
 * @Created by dell
 */

public class API_ {
    public static void main(String[] args) throws Exception {
        //获取本机 InetAddress 对象 getLocalHost
        InetAddress localHost = InetAddress.getLocalHost();
        System.out.println(localHost);
        //根据指定主机名/域名获取 ip 地址对象 getByName
        InetAddress host2 = InetAddress.getByName("DESKTOP-5H1AECH");
        System.out.println(host2);
        InetAddress host3 = InetAddress.getByName("itkxz.cn");
        System.out.println(host3);
        //获取 InetAddress 对象的主机名 getHostName
        String host3Name = host3.getHostName();
        System.out.println(host3Name);
        //获取 InetAddress 对象的地址 getHostAddress
        String host3Address = host3.getHostAddress();
        System.out.println(host3Address);
    }
}
