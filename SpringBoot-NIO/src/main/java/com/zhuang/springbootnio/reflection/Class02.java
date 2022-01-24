package com.zhuang.springbootnio.reflection;

import java.lang.reflect.Field;

/**
 * @Classname Class02
 * @Description 演示Class类的常用方法
 * @Date 2021/6/10 20:26
 * @Created by dell
 */

public class Class02 {
    public static void main(String[] args) throws Exception {
        String classAllPath = "com.zhuang.reflection.Car";
        //1 . 获取到 Car 类 对应的 Class 对象
        //<?> 表示不确定的 Java 类型
        Class<?> cls = Class.forName(classAllPath);

        //2. 输出 cls
        //显示 cls 对象, 是哪个类的 Class 对象 com.hspedu.Car
        System.out.println(cls);
        //输出 cls 运行类型 java.lang.Class
        System.out.println(cls.getClass());

        //3. 得到包名
        System.out.println(cls.getPackage().getName());
        //4. 得到全类名
        System.out.println(cls.getName());
        //5. 通过 cls 创建对象实例
        Car car = (Car) cls.newInstance();
        System.out.println(car);
        //6. 通过反射获取属性 brand
        Field brand = cls.getField("brand");
        System.out.println(brand.get(car));
        //7. 通过反射给属性赋值
        brand.set(car, "奔驰");
        System.out.println(brand.get(car));
        //8 我希望大家可以得到所有的属性(字段)
        System.out.println("=======所有的字段属性====");
        Field[] fields = cls.getFields();
        for (Field f : fields) {
            System.out.println(f.getName());
        }
    }
}
