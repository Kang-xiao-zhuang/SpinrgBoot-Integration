package com.zhuang.springbootnio.reflection;

import java.lang.reflect.Constructor;

/**
 * @Classname ReflecCreateInstance
 * @Description 演示通过反射机制创建实例
 * @Date 2021/6/11 10:21
 * @Created by dell
 */

public class ReflecCreateInstance {
    public static void main(String[] args) throws Exception{
        //1. 先获取到 User 类的 Class 对象
        Class<?> userClass = Class.forName("com.zhuang.reflection.User");
        //2. 通过 public 的无参构造器创建实例
        Object o = userClass.newInstance();
        System.out.println(o);
        //3. 通过 public 的有参构造器创建实例
        /*
        constructor 对象就是
        public User(String name) {//public 的有参构造器
        this.name = name;
        }
        */
        //3.1 先得到对应构造器
        Constructor<?> constructor = userClass.getConstructor(String.class);
        //3.2 创建实例，并传入实参
        Object zk = constructor.newInstance("zk");
        System.out.println("zk=" + zk);
        //4. 通过非 public 的有参构造器创建实例
        //4.1 得到 private 的构造器对象
        Constructor<?> constructor1 = userClass.getDeclaredConstructor(int.class, String.class);
        //4.2 创建实例
        //暴破【暴力破解】 , 使用反射可以访问 private 构造器/方法/属性
        constructor1.setAccessible(true);
        Object user2 = constructor1.newInstance(100, "张三丰");
        System.out.println("user2=" + user2);
    }
}

class User { //User 类
    private int age = 10;
    private String name = "康小庄";
    public User() {//无参 public
    }
    public User(String name) {//public 的有参构造器
        this.name = name;
    }
    private User(int age, String name) {//private 有参构造器
        this.age = age;
        this.name = name;
    }
    @Override
    public String toString() {
        return "User [age=" + age + ", name=" + name + "]";
    }
}

