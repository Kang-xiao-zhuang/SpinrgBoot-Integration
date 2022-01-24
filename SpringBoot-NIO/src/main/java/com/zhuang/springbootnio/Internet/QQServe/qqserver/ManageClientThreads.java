package com.zhuang.springbootnio.Internet.QQServe.qqserver;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @Classname ManageClientThreads
 * @Description 该类用于管理和客户端通信的线程
 * @Date 2021/6/8 12:50
 * @Created by dell
 */

public class ManageClientThreads {
    private static HashMap<String,ServerConnectClientThread> hm=new HashMap<String, ServerConnectClientThread>();

    //返回hm
    public static HashMap<String, ServerConnectClientThread> getHm(){
        return hm;
    }
    //将线程添加对象到hm集合
    public static  void addClientThread(String userId,ServerConnectClientThread serverConnectClientThread){
        hm.put(userId, serverConnectClientThread);
    }

    //根据userId，返回ServerConnectClientThread线程
    public static  ServerConnectClientThread getServerConnectClientThread(String userId) {
        return hm.get(userId);
    }

    //增加一个方法，从集合中，移除某个线程对象
    public static void removeServerConnectClientThread(String userId) {
        hm.remove(userId);
    }

    /*
    返回在线用户列表
 */
    public static String getOnlineUser(){
//        遍历集合
        Iterator<String> iterator = hm.keySet().iterator();
        String onlineUserList="";
        while (iterator.hasNext()){
            onlineUserList+=iterator.next().toString()+" ";
        }
        return onlineUserList;
    }
}


