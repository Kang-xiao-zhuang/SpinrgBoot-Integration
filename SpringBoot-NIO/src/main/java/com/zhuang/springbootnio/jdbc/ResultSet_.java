package com.zhuang.springbootnio.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @Classname ResultSet_
 * @Description ResultSet结果集
 * @Date 2021/6/15 12:53
 * @Created by dell
 */

public class ResultSet_ {
    public static void main(String[] args) throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/mybatis";
        String user = "root";
        String password = "root";
        Connection connection = DriverManager.getConnection(url, user, password);
        //得到 statement对象
        Statement statement = connection.createStatement();
        //sql语句
        String sql = "select id,name,pwd from user";
        // 调用查询方法 返回结果集
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()){
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String pwd = resultSet.getString(3);
            System.out.println(id + "\t" + name + "\t" + pwd );
        }
        resultSet.close();
        statement.close();
        connection.close();
    }
}
