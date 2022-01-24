package com.zhuang.springbootnio.io;

import java.io.Serializable;

/**
 * @Classname Dog
 * @Description 小狗类
 * @Date 2021/5/31 14:58
 * @Created by dell
 */

public class Dog implements Serializable {
    private String name;
    private int age;
    private String country;
    private String color;

    public Dog(String name, int age, String country, String color) {
        this.name = name;
        this.age = age;
        this.country = country;
        this.color = color;
    }
}
