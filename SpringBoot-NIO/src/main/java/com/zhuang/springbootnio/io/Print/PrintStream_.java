package com.zhuang.springbootnio.io.Print;

import java.io.IOException;
import java.io.PrintStream;

/**
 * @Classname PrintStream_
 * @Description 演示 PrintStream （字节打印流/输出流）
 * @Date 2021/5/31 15:27
 * @Created by dell
 */

public class PrintStream_ {
    public static void main(String[] args) throws IOException {
        PrintStream out = System.out;
        //在默认情况下，PrintStream 输出数据的位置是 标准输出，即显示器
        out.println("zk666");
        out.close();
    }
}
