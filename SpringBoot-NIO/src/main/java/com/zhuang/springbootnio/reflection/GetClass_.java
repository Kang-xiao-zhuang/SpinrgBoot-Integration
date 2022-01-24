package com.zhuang.springbootnio.reflection;

/**
 * @Classname GetClass_
 * @Description 演示得到 Class 对象的各种方式(6)
 * @Date 2021/6/10 20:39
 * @Created by dell
 */

public class GetClass_ {
    public static void main(String[] args) throws Exception {
        //1. Class.forName
        String classAllPath = "com.zhuang.reflection.Car";
        Class<?> cls1 = Class.forName(classAllPath);
        System.out.println(cls1);
        //2. 类名.class , 应用场景: 用于参数传递
        Class cls2 = Car.class;
        System.out.println(cls2);
        //3. 对象.getClass(), 应用场景，有对象实例
        Car car = new Car();
        Class cls3 = car.getClass();
        System.out.println(cls3);
        //4. 通过类加载器【4 种】来获取到类的 Class 对象
        //(1)先得到类加载器 car
        ClassLoader classLoader = car.getClass().getClassLoader();
        //(2)通过类加载器得到 Class 对象
        Class cls4 = classLoader.loadClass(classAllPath);
        System.out.println(cls4);


        //5. 基本数据(int, char,boolean,float,double,byte,long,short) 按如下方式得到 Class 类对象
        Class<Integer> integerClass = int.class;
        Class<Character> characterClass = char.class;
        Class<Boolean> booleanClass = boolean.class;
        System.out.println(integerClass);
        System.out.println(characterClass);
        System.out.println(booleanClass);
        //6. 基本数据类型对应的包装类，可以通过 .TYPE 得到 Class 类对象
        Class<Integer> type1 = Integer.TYPE;
        Class<Character> type2 = Character.TYPE;
        System.out.println(type1);
        System.out.println(integerClass.hashCode());
        System.out.println(type1.hashCode());

    }
}
