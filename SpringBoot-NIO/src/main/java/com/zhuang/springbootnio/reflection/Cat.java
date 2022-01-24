package com.zhuang.springbootnio.reflection;

/**
 * @Classname Cat
 * @Description 用一句话描述类的作用
 * @Date 2021/6/10 19:54
 * @Created by dell
 */

public class Cat {
    private String name = "招财猫";
    //public的
    public int age = 10;

    public Cat() {} //无参构造器

    public Cat(String name) {
        this.name = name;
    }

    public void hi() { //常用方法
        //System.out.println("hi " + name);
    }
    public void cry() { //常用方法
        System.out.println(name + " 喵喵叫..");
    }
}
