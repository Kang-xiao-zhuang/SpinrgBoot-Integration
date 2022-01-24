package com.zhuang.springbootnio.jdbc;

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @Classname Druid_
 * @Description Druid连接池
 * @Date 2021/6/17 20:59
 * @Created by dell
 */

public class Druid_ {
    public static void main(String[] args) throws Exception {
        testDruid();
    }

    private static void testDruid() throws Exception {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/mybatis");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

        Connection connection = dataSource.getConnection();
        String sql = "select * from user";
        PreparedStatement prepareStatement = connection.prepareStatement(sql);
        ResultSet resultSet = prepareStatement.executeQuery();
        while (resultSet.next()) {
            Object id = resultSet.getObject(1);
            Object username = resultSet.getObject(2);
            Object password = resultSet.getObject(3);
            System.out.println(id + ":" + username + ":" + password);
        }
        resultSet.close();
        connection.close();
        dataSource.close();
    }
}
