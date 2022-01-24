package com.zhuang.springbootnio.jdbc;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * @Classname C3P0_
 * @Description C3P0连接池
 * @Date 2021/6/17 20:50
 * @Created by dell
 */

public class C3P0_ {
    public static void main(String[] args) throws Exception {
        testC3P0_01();
        testC3P0_02();
    }

    private static void testC3P0_01() throws Exception {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        // 通过配置文件获取相关连接信息
        Properties properties = new Properties();
        properties.load(new FileInputStream("src\\jdbc.properties"));
        //读取相关的属性值
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String url = properties.getProperty("url");
        String driver = properties.getProperty("driver");

        //给数据源 comboPooledDataSource 设置相关的参数
        comboPooledDataSource.setDriverClass(driver);
        comboPooledDataSource.setJdbcUrl(url);
        comboPooledDataSource.setUser(user);
        comboPooledDataSource.setPassword(password);

        //设置初始化连接数
        comboPooledDataSource.setInitialPoolSize(10);
        //最大连接数
        comboPooledDataSource.setMaxPoolSize(50);
        //测试连接池的效率, 测试对 mysql 5000 次操作
        long start = System.currentTimeMillis();
        for (int i = 0; i < 5000; i++) {
            Connection connection = comboPooledDataSource.getConnection();
            //System.out.println("连接 OK");
            connection.close();
        }
        long end = System.currentTimeMillis();
        //c3p0 5000 连接 mysql 耗时=391
        System.out.println("c3p0 5000 连接 mysql 耗时=" + (end - start));

    }

    private static void testC3P0_02() throws Exception {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource("mybatis");
        //测试 5000 次连接 mysql
        long start = System.currentTimeMillis();
        System.out.println("开始执行....");
        for (int i = 0; i < 500000; i++) {
            Connection connection = comboPooledDataSource.getConnection();
            connection.close();
        }
        long end = System.currentTimeMillis();
        System.out.println("c3p0 的第二种方式(500000) 耗时=" + (end - start));
    }
}
