package com.zhuang.springbootnio.io.Buffered;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * @Classname BufferedWriter_
 * @Description 演示 BufferedWriter 的使用
 * @Date 2021/5/29 21:00
 * @Created by dell
 */

public class BufferedWriter_ {
    public static void main(String[] args) throws Exception {
        String filePath = "f:\\ok.txt";
        //创建 BufferedWriter
        //说明:
        //1. new FileWriter(filePath, true) 表示以追加的方式写入
        //2. new FileWriter(filePath) , 表示以覆盖的方式写入
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));
        bufferedWriter.write("hello, 康小庄!");
        bufferedWriter.newLine();//插入一个和系统相关的换行
        bufferedWriter.write("hello2, 康小庄!");
        bufferedWriter.newLine();
        bufferedWriter.write("hello3, 康小庄!");
        bufferedWriter.newLine();
        System.out.println("写入文件成功！");
        //关闭流
        bufferedWriter.close();

    }
}
