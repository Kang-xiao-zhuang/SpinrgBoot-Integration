package com.zhuang.springbootnio.io.HomeWork;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Properties;

/**
 * @Classname HomeWork02
 * @Description HomeWork02
 * @Date 2021/5/31 16:48
 * @Created by dell
 */

public class HomeWork02 {
    public static void main(String[] args) throws Exception {
        String filePath = "f:\\dog.properties";
        Properties properties = new Properties();
        properties.load(new FileReader(filePath));

        String name = (String) properties.get("name");
        int age = Integer.parseInt(properties.get("age")+"");
        String color = (String) properties.get("color");

        Dog dog = new Dog(name, age, color);
        System.out.println(dog);

        //将信息序列化进文件中
        String filePath2="f:\\a2.txt";
        ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(filePath2));
        oos.writeObject(dog);

        oos.close();
        System.out.println("写入文件成功");
    }
}

class Dog implements Serializable {
        private String name;
        private int age;
        private String color;

        public Dog(String name, int age, String color) {
            this.name = name;
            this.age = age;
            this.color = color;
        }

        @Override
        public String toString() {
            return "Dog{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", color='" + color + '\'' +
                    '}';
        }
    }

