package com.zhuang.springbootnio.Internet.HomeWork;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @Classname StreamUtils
 * @Description 用一句话描述类的作用
 * @Date 2021/6/3 13:03
 * @Created by dell
 */

/**
 * 此类用于演示关于流的读写方法
 *
 */
public class StreamUtils {
    /**
     * 功能：将输入流转换成byte[]
     * @param is
     * @return
     * @throws Exception
     */
    public static byte[] streamToByteArray(InputStream is) throws Exception{
        ByteArrayOutputStream bos = new ByteArrayOutputStream();//创建输出流对象
        byte[] b = new byte[1024];
        int len;
        while((len=is.read(b))!=-1){
            bos.write(b, 0, len);
        }
        byte[] array = bos.toByteArray();
        bos.close();
        return array;
    }
    /**
     * 功能：将InputStream转换成String
     * @param is
     * @return
     * @throws Exception
     */

    public static String streamToString(InputStream is) throws Exception{
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder builder= new StringBuilder();
        String line;
        while((line=reader.readLine())!=null){
            builder.append(line+"\r\n");
        }
        return builder.toString();

    }

}
