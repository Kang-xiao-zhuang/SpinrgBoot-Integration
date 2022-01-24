package com.zhuang.springbootnio.io.Object;

import com.zhuang.springbootnio.io.Dog;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @Classname ObjectOutStream_
 * @Description ObjectOutStream实现序列化操作
 * @Date 2021/5/31 14:56
 * @Created by dell
 */
@SuppressWarnings({"all"})
public class ObjectOutStream_{
    public static void main(String[] args) throws IOException {
        //序列化后，保存的文件格式，不是存文本，而是按照他的格式来保存
        String filePath = "f:\\data.dat";
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath));
        //序列化数据到 f:\data.dat
        oos.writeInt(100);// int -> Integer (实现了 Serializable)
        oos.writeBoolean(true);// boolean -> Boolean (实现了 Serializable)
        oos.writeChar('a');// char -> Character (实现了 Serializable)
        oos.writeDouble(9.5);// double -> Double (实现了 Serializable)
        oos.writeUTF("康小庄");//String
        //保存一个 dog 对象
        oos.writeObject(new Dog("旺财", 10, "日本", "白色"));
        oos.close();
        System.out.println("数据保存完毕(序列化形式)");
    }
}
