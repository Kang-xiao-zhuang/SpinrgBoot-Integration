package com.zhuang.springbootnio.Internet.QQClient.views;

import com.zhuang.Intelnet.QQClient.service.FileClientService;
import com.zhuang.Intelnet.QQClient.service.MessageClientService;
import com.zhuang.Intelnet.QQClient.service.UserClientService;
import com.zhuang.Intelnet.QQServe.utils.Utility;

/**
 * @Classname QQView
 * @Description QQ客户端启动类
 * @Date 2021/6/8 14:52
 * @Created by dell
 */
@SuppressWarnings({"all"})
public class QQView {
     //控制是否显示菜单
     private boolean loop = true;
    // 接收用户的键盘输入
    private String key = "";
    //对象是用于登录服务/注册用户
    private UserClientService userClientService = new UserClientService();
    //对象用户私聊/群聊.
    private MessageClientService messageClientService = new MessageClientService();
    //该对象用户传输文件
    private FileClientService fileClientService = new FileClientService();
    public static void main(String[] args) {
        new QQView().mainMenu();
        System.out.println("客户端退出系统.....");
    }

    //显示主菜单
    private void mainMenu() {

        while (loop) {

            System.out.println("===========欢迎登录网络通信系统===========");
            System.out.println("\t\t 1 登录系统");
            System.out.println("\t\t 9 退出系统");
            System.out.print("请输入你的选择: ");
            key = Utility.readString(1);

            //根据用户的输入，来处理不同的逻辑
            switch (key) {
                case "1":
                    System.out.print("请输入用户号: ");
                    String userId = Utility.readString(50);
                    System.out.print("请输入密  码: ");
                    String pwd = Utility.readString(50);
                    //需要到服务端去验证该用户是否合法
                    if (userClientService.checkUser(userId, pwd)) {
                        System.out.println("===========欢迎 (用户 " + userId + " 登录成功) ===========");
                        //进入到二级菜单
                        while (loop) {
                            System.out.println("\n=========网络通信系统二级菜单(用户 " + userId + " )=======");
                            System.out.println("\t\t 1 显示在线用户列表");
                            System.out.println("\t\t 2 群发消息");
                            System.out.println("\t\t 3 私聊消息");
                            System.out.println("\t\t 4 发送文件");
                            System.out.println("\t\t 9 退出系统");
                            System.out.print("请输入你的选择: ");
                            key = Utility.readString(1);
                            switch (key) {
                                case "1":
                                    //写一个方法，来获取在线用户列表
                                    userClientService.onlineFriendList();
                                    break;
                                case "2":
                                    System.out.println("请输入想对大家说的话: ");
                                    String s = Utility.readString(100);
                                    messageClientService.sendMessageToAll(s, userId);
                                    break;
                                case "3":
                                    System.out.print("请输入想聊天的用户号(在线): ");
                                    String getterId = Utility.readString(50);
                                    System.out.print("请输入想说的话: ");
                                    String content = Utility.readString(100);
                                    //编写一个方法，将消息发送给服务器端
                                    messageClientService.sendMessageToOne(content, userId, getterId);
                                    break;
                                case "4":
                                    System.out.print("请输入你想把文件发送给的用户(在线用户): ");
                                    getterId = Utility.readString(50);
                                    System.out.print("请输入发送文件的路径(形式 d:\\xx.jpg)");
                                    String src = Utility.readString(100);
                                    System.out.print("请输入把文件发送到对应的路径(形式 d:\\yy.jpg)");
                                    String dest = Utility.readString(100);
                                    fileClientService.sendFileToOne(src,dest,userId,getterId);
                                    break;
                                case "9":
                                    //调用方法，给服务器发送一个退出系统的message
                                    userClientService.logout();
                                    loop = false;
                                    break;
                            }
                        }
                    } else { //登录服务器失败
                        System.out.println("=========登录失败=========");
                    }
                    break;
                case "9":
                    loop = false;
                    break;
            }

        }

    }
}
