package com.zhuang.springbootnio.io.File;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Classname FileCopy
 * @Description 文件拷贝
 * @Date 2021/5/29 20:28
 * @Created by dell
 */

public class FileCopy {
    public static void main(String[] args) {
        copyFile();
    }

    private static void copyFile() {
        /*
        创建输入流和输出流
         */
        String srcFilePath = "f:\\1.jpg";
        String desFilePath = "f:\\2.jpg";

        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            fis = new FileInputStream(srcFilePath);
            fos = new FileOutputStream(desFilePath);
            //定义字节数组 提高读取效果
            byte[] buf = new byte[1024];
            int readLen=0;

            while ((readLen=fis.read(buf))!=-1){
                //一边读一边写
                fos.write(buf, 0, readLen);
            }
            System.out.println("文件拷贝成功！！！");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
