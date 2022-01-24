package com.zhuang.springbootnio.io.File;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Classname FileInputStream_
 * @Description 演示 FileInputStream 的使用(字节输入流 文件--> 程序)
 * @Date 2021/5/29 20:01
 * @Created by dell
 */

public class FileInputStream_ {
    public static void main(String[] args) throws IOException {
        //  readFile01();
        readFile02();

    }


    private static void readFile01() throws IOException {
        String filePath = "f:\\a.txt";
        int readData = 0;

        FileInputStream fileInputStream = null;

        try {
            //创建fileInputStream对象 用于读取文件
            fileInputStream = new FileInputStream(filePath);
            /*
            从该输入流读取一个字节的数据，如果没有输入可用，返回-1 表示读取完毕
             */
            while ((readData = fileInputStream.read()) != -1) {
                System.out.print((char) readData);
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            fileInputStream.close();
        }
    }


    /*
     * 使用 read(byte[] b) 读取文件，提高效率
     */


    private static void readFile02() {

        String filePath = "f:\\a.txt";
        byte[] buf = new byte[1024];
        int readData = 0;

        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(filePath);
            /*
            从输入流最多读取b.length字节的数据到字节数组  返回-1 读取完毕
            读取正常 返回实际读取的字节数
             */
            while ((readData = fileInputStream.read(buf)) != -1) {
                System.out.print(new String(buf, 0, readData));//显示
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
