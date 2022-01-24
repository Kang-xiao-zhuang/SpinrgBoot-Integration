package com.zhuang.springbootnio.io.HomeWork;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @Classname HomeWork01
 * @Description 用一句话描述类的作用
 * @Date 2021/5/31 16:35
 * @Created by dell
 */

public class HomeWork01 {
    public static void main(String[] args) throws IOException {
        String dirPath = "f:\\mytemp";
        File file = new File(dirPath);
        if (!file.exists()) {
            if (file.mkdirs()) {
                System.out.println("创建" + dirPath + "成功");
            } else {
                System.out.println("创建" + dirPath + "失败");
            }
        }

        String filePath = dirPath + "\\hello.txt";
        file = new File(filePath);
        if (!file.exists()) {
            if (file.createNewFile()) {
                System.out.println(filePath + "创建成功");
                BufferedWriter bw=new BufferedWriter(new FileWriter(file));
                bw.write("哈哈哈！！！");
                bw.close();
            } else {
                System.out.println(filePath + "创建失败");
            }
        } else {
            System.out.println("文件已经存在");
        }
    }
}
