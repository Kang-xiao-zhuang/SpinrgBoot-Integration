package com.zhuang.springbootnio.io.File;

import java.io.FileReader;
import java.io.IOException;

/**
 * @Classname FileReader_
 * @Description FileReader
 * @Date 2021/5/29 21:48
 * @Created by dell
 */

public class FileReader_ {
    public static void main(String[] args) throws Exception {
       // readFile01();
        readFile02();
    }
    public static void readFile01() {
        String filePath = "f:\\d.txt";
        FileReader fileReader = null;
        int data = 0;
        //1. 创建 FileReader 对象
        try {
            fileReader = new FileReader(filePath);
        //循环读取 使用 read, 单个字符读取
            while ((data = fileReader.read()) != -1) {
                System.out.print((char) data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 字符数组读取文件
     */
    public static void readFile02() throws Exception {
        System.out.println("===readFile02===");
        String filePath = "f:\\d.txt";
        FileReader fileReader = null;
        int readLen = 0;
        char[] buf = new char[8];
        //1. 创建 FileReader 对象
        try {
            fileReader = new FileReader(filePath);
        //循环读取 使用 read(buf), 返回的是实际读取到的字符数
        //如果返回-1, 说明到文件结束
            while ((readLen = fileReader.read(buf)) != -1) {
                System.out.print(new String(buf, 0, readLen));
            }
        } catch(IOException e){
            e.printStackTrace();
        } finally{
            try {
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
