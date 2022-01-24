package com.zhuang.springbootnio.io.Object;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * @Classname ObjectInputStream_
 * @Description ObjectInputStream实现反序列化
 * @Date 2021/5/31 15:00
 * @Created by dell
 */

public class ObjectInputStream_ {
    public static void main(String[] args) throws Exception {
        //1.创建流对象
        ObjectInputStream ois=new ObjectInputStream(new FileInputStream("f:\\data.dat"));
        // 2.读取， 注意顺序
        System.out.println(ois.readInt());
        System.out.println(ois.readBoolean());
        System.out.println(ois.readChar());
        System.out.println(ois.readDouble());
        System.out.println(ois.readUTF());
        // 3.关闭
        ois.close();
        System.out.println("以反序列化的方式读取(恢复)ok~");
    }
}
