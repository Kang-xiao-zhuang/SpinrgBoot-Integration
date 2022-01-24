package com.zhuang.springbootnio.reflection;

import java.io.Serializable;

/**
 * @Classname AllTypeClass
 * @Description 演示哪些类型有 Class 对象
 * @Date 2021/6/10 21:08
 * @Created by dell
 */

public class AllTypeClass {
    public static void main(String[] args) throws Exception {
        //外部类
        Class<String> cls1 = String.class;
        //接口
        Class<Serializable> cls2 = Serializable.class;
        //数组
        Class<Integer[]> cls3 = Integer[].class;
        //二维数组
        Class<float[][]> cls4 = float[][].class;
        //注解
        Class<Deprecated> cls5 = Deprecated.class;
        //枚举
        Class<Thread.State> cls6 = Thread.State.class;
        //基本数据类型
        Class<Long> cls7 = long.class;
        //void 数据类型
        Class<Void> cls8 = void.class;
        Class<Class> cls9 = Class.class;
        System.out.println(cls1);
        System.out.println(cls2);
        System.out.println(cls3);
        System.out.println(cls4);
        System.out.println(cls5);
        System.out.println(cls6);
        System.out.println(cls7);
        System.out.println(cls8);
        System.out.println(cls9);
    }
}
