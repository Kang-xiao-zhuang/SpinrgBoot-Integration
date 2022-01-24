package com.zhuang.springbootnio.reflection;

import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * @Classname Reflection01
 * @Description 反射类01
 * @Date 2021/6/10 19:51
 * @Created by dell
 */

public class Reflection01 {
    public static void main(String[] args) throws Exception{
        //使用Properties类 ，读写配置文件
        Properties properties = new Properties();
        properties.load(new FileInputStream("f:\\re.properties"));
        String classfullPath=properties.get("classfullpath").toString();
        String methodName=properties.get("method").toString();
        System.out.println(classfullPath);
        System.out.println(methodName);

        //使用反射机制解决
        //加载类，返回Class类型对象
        Class cls = Class.forName("com.zhuang.reflection.Cat");
        //通过cls的到加载类的对象实例
        Object o = cls.newInstance();
        System.out.println("o的运行类型="+o.getClass());
        //通过cls的加载的类 的方法
        Method method = cls.getMethod(methodName);
        System.out.println("方法是"+method);
        //通过method调用方法，通过方法对象来实现调用方法
        /*
        传统方法 对象.方法()
        反射机制 方法.invoke(对象)
         */
        method.invoke(o);

        //java.lang.reflect.Field: 代表类的成员变量, Field 对象表示某个类的成员变量
        //得到 name 字段
        //getField 不能得到私有的属性
        Field nameField = cls.getField("age");
        // 传统写法 对象.成员变量 , 反射 : 成员变量对象.get(对象)
        System.out.println(nameField.get(o));
    //java.lang.reflect.Constructor: 代表类的构造方法, Constructor 对象表示构造器
        //()中可以指定构造器参数类型, 返回无参构造器
        Constructor constructor = cls.getConstructor();
        System.out.println(constructor);
    }
}
