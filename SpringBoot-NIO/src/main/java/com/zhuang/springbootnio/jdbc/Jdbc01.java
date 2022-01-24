package com.zhuang.springbootnio.jdbc;

import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;

/**
 * @Classname Jdbc01
 * @Description 示范如何使用Jdbc
 * @Date 2021/6/15 8:50
 * @Created by dell
 */

public class Jdbc01 {
    public static void main(String[] args) throws Exception {
        //注册驱动
        Driver driver = new Driver();
        //sql语句
        /**
         *  jdbc:mysql: 规定好的协议，通过jdbc的方式连接mysql
         *  localhost 表示主机
         *  3306 表示mysql监听的端口
         *  mybatis表示哪个数据库
         */
        String url = "jdbc:mysql://localhost:3306/mybatis";
        //将账号密码 放入到Properties
        Properties properties = new Properties();
        //账号密码
        properties.setProperty("user", "root");
        properties.setProperty("password", "root");
        Connection connect = driver.connect(url, properties);

        //执行sql
        String sql = "insert into user value ('10','张三','123')";
        //statement用于执行静态SQL语句并返回其生成的结果的对象
        Statement statement = connect.createStatement();
        int row = statement.executeUpdate(sql);
        System.out.println(row > 0 ? "成功" : "失败");

        statement.close();
        connect.close();
    }
}
