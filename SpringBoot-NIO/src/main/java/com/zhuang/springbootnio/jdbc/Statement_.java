package com.zhuang.springbootnio.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

/**
 * @Classname Statement_
 * @Description Statement演示
 * @Date 2021/6/15 13:17
 * @Created by dell
 */

public class Statement_ {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        //让用户输入管理员名和密码
        System.out.print("请输入管理员的名字: ");
        //next(): 当接收到 空格或者 '就是表示结束
        String adminName = scanner.nextLine();
        //如果希望看到 SQL 注入，这里需要用 nextLine
        System.out.print("请输入管理员的密码: ");
        String adminPwd = scanner.nextLine();
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/mybatis";
        String user = "root";
        String password = "root";
        Connection connection = DriverManager.getConnection(url, user, password);
        //得到 statement对象
        Statement statement = connection.createStatement();
        //sql语句
        String sql = "select name,pwd from admin " +
                "where name ='" + adminName + "' and pwd = '" + adminPwd + "'";
        ResultSet resultSet = statement.executeQuery(sql);

        if (resultSet.next()) {
            System.out.println("查询成功");
        } else {
            System.out.println("查询失败");
        }
        connection.close();
        resultSet.close();
        statement.close();
    }
}
