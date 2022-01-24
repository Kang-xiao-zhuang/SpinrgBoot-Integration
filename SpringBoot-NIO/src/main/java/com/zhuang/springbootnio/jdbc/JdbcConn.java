package com.zhuang.springbootnio.jdbc;

import com.mysql.jdbc.Driver;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * @Classname JdbcConn
 * @Description java 连接 mysql 的 5种方式
 * @Date 2021/6/15 9:07
 * @Created by dell
 */

public class JdbcConn {
    public static void main(String[] args) throws Exception {
//        connect01();
//        connect02();
//        connect03();
        connect04();
//        connect05();
    }

    private static void connect01() throws Exception {
        //创建Driver对象
        Driver driver = new Driver();
        String url = "jdbc:mysql://localhost:3306/mybatis";
        //将账号密码 放入到Properties
        Properties properties = new Properties();
        //账号密码
        properties.setProperty("user", "root");
        properties.setProperty("password", "root");
        Connection connect = driver.connect(url, properties);
        System.out.println(connect);
    }

    private static void connect02() throws Exception {
        //使用 DriverManager 替代 driver 进行统一管理
        //使用反射加载 Driver 类 , 动态加载，更加的灵活，减少依赖性
        Class<?> aClass = Class.forName("com.mysql.jdbc.Driver");

        Driver driver = (Driver) aClass.newInstance();

        String url = "jdbc:mysql://localhost:3306/mybatis";
        //将账号密码 放入到Properties
        Properties properties = new Properties();
        //账号密码
        properties.setProperty("user", "root");
        properties.setProperty("password", "root");
        Connection connect = driver.connect(url, properties);
        System.out.println("方式 2=" + connect);
    }

    private static void connect03() throws Exception {
        //使用反射加载 Driver 类 , 动态加载，更加的灵活，减少依赖性
        Class<?> aClass = Class.forName("com.mysql.jdbc.Driver");

        Driver driver = (Driver) aClass.newInstance();
        String url = "jdbc:mysql://localhost:3306/mybatis";
        String user = "root";
        String password = "root";
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println("方式3=" + connection);
    }

    private static void connect04() throws Exception {
        //使用反射加载了 Driver 类
        //在加载 Driver 类时，完成注册
        /*
        源码: 1. 静态代码块，在类加载时，会执行一次. 2. DriverManager.registerDriver(new Driver());
        3. 因此注册 driver 的工作已经完成
        static {
        try {
        DriverManager.registerDriver(new Driver());
        } catch (SQLException var1) {
        throw new RuntimeException("Can't register driver!");
        }
        }
        */
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/mybatis";
        String user = "root";
        String password = "root";
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println("方式4=" + connection);
    }

    private static void connect05() throws Exception {
        //通过 Properties 对象获取配置文件的信息
        Properties properties = new Properties();
        properties.load(new FileInputStream("src\\jdbc.properties"));
//获取相关的值
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String driver = properties.getProperty("driver");
        String url = properties.getProperty("url");
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, user, password);
        System.out.println("方式 5 " + connection);
    }
}
