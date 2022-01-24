package com.zhuang.springbootnio.io.Buffered;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @Classname BufferedReader_
 * @Description * 演示 bufferedReader 使用
 * @Date 2021/5/29 20:52
 * @Created by dell
 */

public class BufferedReader_ {
    public static void main(String[] args) throws Exception {
        String filePath = "f:\\note.txt";

        //创建BufferedReader
        BufferedReader  bufferedReader=new BufferedReader(new FileReader(filePath));
        try {
            //按行读取 效率高
            String line;
            //1. bufferedReader.readLine() 是按行读取文件
            //2. 当返回 null 时，表示文件读取完毕
            while ((line = bufferedReader.readLine())!=null){
                System.out.println(line);
            }
           // 关闭流, 这里注意，只需要关闭 BufferedReader ，因为底层会自动的去关闭 节点流
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            bufferedReader.close();
        }
    }
}
