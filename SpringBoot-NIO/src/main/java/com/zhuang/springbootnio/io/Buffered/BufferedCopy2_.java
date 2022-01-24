package com.zhuang.springbootnio.io.Buffered;

import java.io.*;

/**
 * @Classname BufferedCopy2_
 * @Description  演示使用 BufferedOutputStream 和 BufferedInputStream 使用 完成文件的拷贝
 * @Date 2021/5/31 14:50
 * @Created by dell
 */

public class BufferedCopy2_ {
    public static void main(String[] args) throws IOException {
        String srcFilePath = "f:\\a.txt";
        String destFilePath = "f:\\a2.txt";
        //创建 BufferedOutputStream 对象 BufferedInputStream 对象
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
        //因为 FileInputStream 是 InputStream 子类
            bis = new BufferedInputStream(new FileInputStream(srcFilePath));
         bos = new BufferedOutputStream(new FileOutputStream(destFilePath));
        //循环的读取文件，并写入到 destFilePath
        byte[] buff = new byte[1024];
        int readLen = 0;
        //当返回 -1 时，就表示文件读取完毕
        while ((readLen = bis.read(buff)) != -1) {
            bos.write(buff, 0, readLen);
        }
        System.out.println("文件拷贝完毕~~~");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }finally {
            bis.close();
            bos.close();
        }
    }
}

