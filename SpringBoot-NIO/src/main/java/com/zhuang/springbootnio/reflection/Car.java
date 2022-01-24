package com.zhuang.springbootnio.reflection;

/**
 * @Classname Car
 * @Description 用一句话描述类的作用
 * @Date 2021/6/10 19:55
 * @Created by dell
 */

public class Car {
    public String brand = "宝马";//品牌
    public int price = 500000;
    public String color = "白色";

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", price=" + price +
                ", color='" + color + '\'' +
                '}';
    }
}
