package com.zhuang.springbootnio.reflection.HomeWork;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @Classname HomeWork02
 * @Description 作业2
 * @Date 2021/6/11 11:36
 * @Created by dell
 */

/**
 * 利用Class类的forName方法得到File类的class 对象
 * 在控制台打印File类的所有构造器
 * 通过newInstance的方法创建File对象，并创建F:\my.txt文件
 */
public class HomeWork02 {
    public static void main(String[] args) throws Exception{
        //获取类class对象
        Class<?> cls = Class.forName("java.io.File");
        //遍历cls类对象构造器
        for (Constructor<?> declaredConstructor : cls.getDeclaredConstructors()) {
            System.out.println("构造方法"+declaredConstructor);
        }
        //指定的得到 public java.io.File(java.lang.String)
        Constructor<?> declaredConstructor = cls.getDeclaredConstructor(String.class);
        String filePath="f:\\my.text";
        Object file = declaredConstructor.newInstance(filePath);
        //得到createNewFile 的方法对象
        Method createNewFile = cls.getMethod("createNewFile");
        createNewFile.invoke(file);

        System.out.println(file.getClass());
        System.out.println("文件创建成功"+filePath);
    }
}
