package com.zhuang.springbootnio.reflection.HomeWork;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Classname HomeWork01
 * @Description 作业1
 * @Date 2021/6/11 10:59
 * @Created by dell
 */

public class HomeWork01 {
    public static void main(String[] args) throws Exception{
        /**
         * 定义Student类，有私有name属性，并且属性值为hellokitty
         * 提供getName的公有方法
         * 创建Student的类，利用Class类得到私有的name属性，修改私有的name属性值，并调用getName()的方法打印name属性值
         */
        //1. 得到 Student类对应的Class对象
        Class<Student> studentClass = Student.class;
        //创建对象实例
        Student student = studentClass.newInstance();
        //得到name属性对象
        Field name = studentClass.getDeclaredField("name");
        // 设置权限
        name.setAccessible(true);
        //赋值
        name.set(student,"康小庄");
        //获得方法
        Method getName = studentClass.getMethod("getName");
        //调用
        Object invoke = getName.invoke(student);
        System.out.println(invoke);
    }
}

class Student {
    private String name = "hellokitty";
    //默认无参构造器
    public String getName() {
        return name;
    }
}