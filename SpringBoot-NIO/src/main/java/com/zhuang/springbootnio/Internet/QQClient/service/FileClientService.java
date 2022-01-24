package com.zhuang.springbootnio.Internet.QQClient.service;

import com.zhuang.springbootnio.Internet.QQServe.qqcommon.Message;
import com.zhuang.springbootnio.Internet.QQServe.qqcommon.MessageType;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @Classname FileClientService
 * @Description 文件传输类
 * @Date 2021/6/8 14:48
 * @Created by dell
 */

public class FileClientService {
    /**
     * @param src      源文件
     * @param dest     把该文件传输到对方的哪个目录
     * @param senderId 发送用户id
     * @param getterId 接收用户id
     */
    public void sendFileToOne(String src, String dest, String senderId, String getterId) {

        //读取src文件  -->  message
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_FILE_MES);
        message.setSender(senderId);
        message.setGetter(getterId);
        message.setSrc(src);
        message.setDest(dest);

        //需要将文件读取
        FileInputStream fileInputStream = null;
        //获取文件的长度 写入字节数组中
        byte[] fileBytes = new byte[(int) new File(src).length()];

        try {
            fileInputStream = new FileInputStream(src);
            //将src文件读入到程序的字节数组
            fileInputStream.read(fileBytes);
            //将文件对应的字节数组设置message
            message.setFileBytes(fileBytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //提示信息
        System.out.println("\n" + senderId + " 给 " + getterId + " 发送文件: " + src
                + " 到对方的电脑的目录 " + dest);
        //发送
        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(senderId).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
