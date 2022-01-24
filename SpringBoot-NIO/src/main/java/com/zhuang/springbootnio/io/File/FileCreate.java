package com.zhuang.springbootnio.io.File;

import java.io.File;
import java.io.IOException;

/**
 * @Classname FileCreate
 * @Description 演示创建文件的3种方法
 * @Date 2021/5/29 19:40
 * @Created by dell
 */

public class FileCreate {
    public static void main(String[] args) {
      //  create01();
        create03();
      //  create02();
    }

    // 方式1 new File(String pathname)
    public static void create01(){
        String filePath = "f:\\a.txt";
        File file = new File(filePath);

        try {
            file.createNewFile();
            System.out.println("文件创建成功！");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("文件创建失败！");
        }
    }

    ////方式 2 new File(File parent,String child) //根据父目录文件+子路径构建
    public static void create02(){
        File parentFile = new File("f:\\");
        String fileName="b.txt";

        File file = new File(parentFile, fileName);

        try {
            file.createNewFile();
            System.out.println("创建成功");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("创建失败");
        }
    }

    //方式 3 new File(String parent,String child) //根据父目录+子路径构建
    public static void create03(){
        File parentPath = new File("f:\\");
        String fileName="c.txt";

        File file = new File(parentPath, fileName);

        try {
            file.createNewFile();
            System.out.println("创建成功");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("创建失败");
        }
    }
}
