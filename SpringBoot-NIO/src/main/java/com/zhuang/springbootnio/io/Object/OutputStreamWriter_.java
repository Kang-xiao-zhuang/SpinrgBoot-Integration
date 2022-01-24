package com.zhuang.springbootnio.io.Object;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

/**
 * @Classname OutputStreamWriter_
 * @Description OutputStreamWriter写入数据
 * @Date 2021/5/31 15:18
 * @Created by dell
 */

public class OutputStreamWriter_ {
    public static void main(String[] args) throws Exception{
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("f:\\a2.txt"));
        osw.write("康小庄666");
        osw.close();
        System.out.println("写入数据成功！");
    }
}
