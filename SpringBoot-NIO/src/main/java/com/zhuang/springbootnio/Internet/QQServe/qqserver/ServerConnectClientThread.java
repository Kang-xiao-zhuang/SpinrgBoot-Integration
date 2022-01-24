package com.zhuang.springbootnio.Internet.QQServe.qqserver;

import com.zhuang.springbootnio.Internet.QQServe.qqcommon.Message;
import com.zhuang.springbootnio.Internet.QQServe.qqcommon.MessageType;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @Classname ServerConnectClientThread
 * @Description 该类的一个对象和某个客户端保持通信
 * @Date 2021/6/8 12:51
 * @Created by dell
 */

public class ServerConnectClientThread extends Thread{

    private Socket socket;
    //连接服务端的id
    private String userId;

    public ServerConnectClientThread(Socket socket, String userId){
        this.socket = socket;
        this.userId = userId;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        label:
        while (true) {
            try {
                System.out.println("服务端和客户端" + userId + " 保持通信，读取数据中！");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
                //后面根据MessageType的类型做相应的业务处理
                switch (message.getMesType()) {
                    case MessageType.MESSAGE_GET_ONLINE_FRIEND: {
                        //显示客户端在线列表
                        System.out.println(message.getSender() + " 要在线用户列表");
                        String onlineUser = ManageClientThreads.getOnlineUser();
                    /*
                    返回message
                    构建一个Message对象 返回客户端
                     */
                        Message message2 = new Message();
                        message2.setMesType(MessageType.MESSAGE_RET_ONLINE_FRIEND);
                        message2.setContent(onlineUser);
                        message2.setGetter(message.getSender());
                        //返回给客户端
                        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                        oos.writeObject(message2);
                        break;
                    }
                    case MessageType.MESSAGE_COMM_MES: {
                        //根据message获取id 然后得到对应线程
                        ServerConnectClientThread serverConnectClientThread
                                = ManageClientThreads.getServerConnectClientThread(message.getGetter());
                        //得到对应Socket的对象输出流，将message对象转发给指定的客户端
                        ObjectOutputStream oos
                                = new ObjectOutputStream(serverConnectClientThread.getSocket().getOutputStream());
                        oos.writeObject(message);
                        break;
                    }
                    case MessageType.MESSAGE_TO_ALL_MES:
                        //需要遍历 管理线程的集合 把所有的线程的socket得到 得到把message进行转发即可
                        HashMap<String, ServerConnectClientThread> hm = ManageClientThreads.getHm();
                        Iterator<String> iterator = hm.keySet().iterator();
                        while (iterator.hasNext()) {
                            //取出在线用户id
                            String onLineUserId = iterator.next().toString();
                            if (!onLineUserId.equals(message.getSender())) {
                                //排除群发消息的用户
                                //进行转发消息message
                                ObjectOutputStream oos
                                        = new ObjectOutputStream(hm.get(onLineUserId).getSocket().getOutputStream());
                                oos.writeObject(message);
                            }
                        }
                        break;
                    case MessageType.MESSAGE_FILE_MES: {
                        //根据getter id 获取到对应的线程 ，将message对象转发
                        ObjectOutputStream oos = new ObjectOutputStream(ManageClientThreads
                                .getServerConnectClientThread(message.getGetter()).getSocket().getOutputStream());
                        //转发
                        oos.writeObject(message);
                        break;
                    }
                    case MessageType.MESSAGE_CLIENT_EXIT:
                        //客户端退出
                        System.out.println(message.getSender() + " 退出");
                        //这个客户端从对应线程删除
                        ManageClientThreads.removeServerConnectClientThread(message.getSender());
                        socket.close();
                        break label;
                    default:
                        System.out.println("其他类型的message,暂时不处理");
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
