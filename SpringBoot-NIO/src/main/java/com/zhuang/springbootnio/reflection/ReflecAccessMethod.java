package com.zhuang.springbootnio.reflection;

import java.lang.reflect.Method;

/**
 * @Classname ReflecAccessMethod
 * @Description 演示通过反射调用方法
 * @Date 2021/6/11 10:39
 * @Created by dell
 */

public class ReflecAccessMethod {
    public static void main(String[] args) throws Exception{
        //得到Boss类对应的Class对象
        Class<?> bossClass = Class.forName("com.zhuang.reflection.Boss");
        //创建对象
        Object o = bossClass.newInstance();
        //调用public 方法 得到方法对象
        Method show = bossClass.getMethod("show");
        //调用
        show.invoke(o);

        //调用private方法
        Method say = bossClass.getDeclaredMethod("say",int.class);
        //say方法是private，需要设置权限
        say.setAccessible(true);
        say.invoke(o,5);
    }
}

class Boss{
    private int age;
    private String name;

    public Boss() {//无参 public
    }
    public Boss(String name) {//public 的有参构造器
        this.name = name;
    }
    private Boss(int age, String name) {//private 有参构造器
        this.age = age;
        this.name = name;
    }

    public void show(){
        System.out.println("我是老板哈哈哈");
    }
    private void say(int i){
        System.out.println("我是老板嘿嘿嘿->"+i);
    }
}
