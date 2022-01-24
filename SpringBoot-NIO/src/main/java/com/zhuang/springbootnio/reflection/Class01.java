package com.zhuang.springbootnio.reflection;

/**
 * @Classname Class01
 * @Description 用一句话描述类的作用
 * @Date 2021/6/10 20:21
 * @Created by dell
 */

public class Class01 {
    public static void main(String[] args) throws Exception{
        //某个类的对象Class ，在内存中只有一份，类只加载一次
        Class<?> cls1 = Class.forName("com.zhuang.reflection.Cat");
        Class<?> cls2 = Class.forName("com.zhuang.reflection.Cat");

        System.out.println(cls1.hashCode());
        System.out.println(cls2.hashCode());

    }
}
