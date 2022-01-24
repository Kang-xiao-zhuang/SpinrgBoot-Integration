package com.zhuang.springbootnio.io.File;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @Classname FileWriter_
 * @Description 用一句话描述类的作用
 * @Date 2021/5/29 20:44
 * @Created by dell
 */
@SuppressWarnings({"all"})
public class FileWriter_ {
    public static void main(String[] args) {
        String filePath = "f:\\note.txt";
        //创建 FileWriter 对象
        FileWriter fileWriter = null;
        char[] chars = {'a', 'b', 'c'};
        try {
            fileWriter = new FileWriter(filePath);//默认是覆盖写入
        // 3) write(int):写入单个字符
            fileWriter.write('H');
        // 4) write(char[]):写入指定数组
            fileWriter.write(chars);
        // 5) write(char[],off,len):写入指定数组的指定部分
            fileWriter.write("康小庄2333".toCharArray(), 0, 3);
        // 6) write（string）：写入整个字符串
            fileWriter.write(" 你好北京~");
            fileWriter.write("风雨之后，定见彩虹");
            // 7) write(string,off,len):写入字符串的指定部分
            fileWriter.write("上海天津", 0, 2);
        //在数据量大的情况下，可以使用循环操作
            System.out.println("写入文件成功");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                //一定要关闭流 才能写入数据
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
