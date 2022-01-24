package com.zhuang.springbootnio.io.Object;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * @Classname InputStreamReader_
 * @Description InputStreamReader
 * @Date 2021/5/31 15:09
 * @Created by dell
 */

public class InputStreamReader_ {
    public static void main(String[] args) throws Exception{
        String filePath = "f:\\a.txt";
        //1. 把 FileInputStream 转成 InputStreamReader
        //2. 指定编码 gbk
        //InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath), "gbk");
        //3. 把 InputStreamReader 传入 BufferedReader
        //BufferedReader br = new BufferedReader(isr);
        //将 2 和 3 合在一起
        BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(filePath), "gbk"));
        //4. 读取
        String s = br.readLine();
        System.out.println("读取内容=" + s);
        //5. 关闭外层流
        br.close();
    }
}
