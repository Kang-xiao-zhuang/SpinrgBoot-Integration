package com.zhuang.springbootnio.io.Buffered;

import java.io.*;

/**
 * @Classname BufferedCopy_
 * @Description 用一句话描述类的作用
 * @Date 2021/5/29 21:07
 * @Created by dell
 */

public class BufferedCopy_ {
    public static void main(String[] args) {
        String srcFilePath = "f:\\1.jpg";
        String desFilePath="f:\\3.jpg";
        BufferedReader br=null;
        BufferedWriter bw=null;
        //定义行
        String line;

        try {
            br=new BufferedReader(new FileReader(srcFilePath));
            bw=new BufferedWriter(new FileWriter(desFilePath));
            while ((line= br.readLine())!=null){
                bw.write(line);
                //插入新的换行
                bw.newLine();
            }
            System.out.println("拷贝成功！");
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                br.close();
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
