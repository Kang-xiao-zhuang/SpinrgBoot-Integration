package com.zhuang.springbootnio.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Scanner;

/**
 * @Classname PreparedStatement_
 * @Description PreparedStatement使用
 * @Date 2021/6/15 13:32
 * @Created by dell
 */

public class PreparedStatement_ {
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
        //  String sql = "select * from admin where NAME = ? and pwd = ?";
        //String sql = "insert into admin values(?, ?)";
        //preparedStatement 对象实现了 PreparedStatement 接口的实现类的对象
        String sql = "delete from admin where name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        //给 ? 赋值
        preparedStatement.setString(1, adminName);
     //   preparedStatement.setString(2, adminPwd);
        // ResultSet resultSet = preparedStatement.executeQuery();
     //   int i = preparedStatement.executeUpdate();
        /*if (resultSet.next()) {
            System.out.println("查询成功");
        } else {
            System.out.println("查询失败");
        }*/
     //   System.out.println(i > 0 ? "成功" : "失败");
        connection.close();
        // resultSet.close();
        statement.close();
    }
}
