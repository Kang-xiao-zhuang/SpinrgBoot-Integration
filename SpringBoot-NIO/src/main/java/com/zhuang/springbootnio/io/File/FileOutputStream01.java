package com.zhuang.springbootnio.io.File;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Classname FileOutputStream01
 * @Description 演示使用 FileOutputStream 将数据写到文件中, * 如果该文件不存在，则创建该文件
 * @Date 2021/5/29 20:17
 * @Created by dell
 */

public class FileOutputStream01 {
    public static void main(String[] args) {
        writeFile();
    }

    private static void writeFile() {
        String filePath = "f:\\d.txt";

        FileOutputStream fileOutputStream=null;

        try {
            //1. new FileOutputStream(filePath) 创建方式，当写入内容是，会覆盖原来的内容
           //2. new FileOutputStream(filePath, true) 创建方式，当写入内容是，是追加到文件后面
            fileOutputStream=new FileOutputStream(filePath,true);
            //写入一个字节
            fileOutputStream.write('K');
            //写入字符串
            String str="kangxiaozhuang";
            //转为字节数组
            byte[] bytes = str.getBytes("UTF-8");
            //按照编码的形式写入文件中
            fileOutputStream.write(str.getBytes("UTF-8"));

            /*
            write(byte[] b, int off, int len)
            将 len 字节从位于偏移量 off 的指定字节数组写入此文件输出流
             */
            fileOutputStream.write(str.getBytes(), 0, 3);
            System.out.println("写入文件成功");
        } catch (Exception e) {
            System.out.println("写入文件失败");
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
