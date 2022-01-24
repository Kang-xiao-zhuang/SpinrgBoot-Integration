package com.zhuang.springbootnio.Internet.QQServe.qqserver;

import com.zhuang.Intelnet.QQServe.qqcommon.Message;
import com.zhuang.Intelnet.QQServe.qqcommon.MessageType;
import com.zhuang.Intelnet.QQServe.utils.Utility;

import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @Classname SendNewsToAllService
 * @Description 用一句话描述类的作用
 * @Date 2021/6/8 14:23
 * @Created by dell
 */

public class SendNewsToAllService implements Runnable{
    @Override
    public void run() {
        while (true) {
            //为了多次推送新闻可以使用while循环
            System.out.println("输入服务器要推送的新闻/消息[输入exit表示退出服务线程]");
            String news= Utility.readString(100);
            if ("exit".equals(news)){
                break;
            }
            //构建一个消息，群发信息
            Message message=new Message();
            message.setSender("服务器");
            message.setMesType(MessageType.MESSAGE_TO_ALL_MES);
            message.setContent(news);
            message.setSendTime(new Date().toString());
            System.out.println("服务器推送消息给所有人 说"+news);

            //遍历当前所有的通信线程，得到socket,并发送message
            HashMap<String, ServerConnectClientThread> hm = ManageClientThreads.getHm();
            Iterator<String> iterator = hm.keySet().iterator();
            while (iterator.hasNext()){
                String onLineUserId = iterator.next().toString();
                try {
                    ObjectOutputStream oos
                            = new ObjectOutputStream(hm.get(onLineUserId).getSocket().getOutputStream());
                    oos.writeObject(message);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
