package com.zhuang.springbootnio.Internet.QQClient.service;

import java.util.HashMap;

/**
 * @Classname ManageClientConnectServerThread
 * @Description 线程客户端管理
 * @Date 2021/6/8 14:48
 * @Created by dell
 */

public class ManageClientConnectServerThread {
    //我们把多个线程放入一个HashMap集合，key 就是用户id, value 就是线程
    private static HashMap<String, ClientConnectServerThread> hm = new HashMap<>();

    //将某个线程加入到集合
    public static void addClientConnectServerThread(String userId, ClientConnectServerThread clientConnectServerThread) {
        hm.put(userId, clientConnectServerThread);
    }
    //通过userId 可以得到对应线程
    public static ClientConnectServerThread getClientConnectServerThread(String userId) {
        return hm.get(userId);
    }

}
