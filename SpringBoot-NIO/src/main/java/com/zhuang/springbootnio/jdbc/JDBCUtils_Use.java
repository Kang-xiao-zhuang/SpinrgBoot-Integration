package com.zhuang.springbootnio.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Classname JDBCUtils_Use
 * @Description JDBCUtils_Use演示工具类的使用
 * @Date 2021/6/16 20:57
 * @Created by dell
 */

public class JDBCUtils_Use {
    public static void main(String[] args) {
        //   testSelect();
        testDML();
    }


    public static void testSelect() {
        //1. 得到连接
        Connection connection = null;
        //2. 组织一个 sql
        String sql = "select * from user where id = ?";
        PreparedStatement preparedStatement = null;
        ResultSet set = null;
        //3. 创建 PreparedStatement 对象
        try {
            connection = JDBCUtils.getConnection();
            //com.mysql.jdbc.JDBC4Connection
            System.out.println(connection.getClass());
            preparedStatement = connection.prepareStatement(sql);
            //给?号赋值
            preparedStatement.setInt(1, 2);
            //执行, 得到结果集
            set = preparedStatement.executeQuery();
            //遍历该结果集
            while (set.next()) {
                int id = set.getInt("id");
                String name = set.getString("name");
                String pwd = set.getString("pwd");
                System.out.println(id + "\t" + name + "\t" + pwd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            JDBCUtils.close(set, preparedStatement, connection);
        }
    }

    private static void testDML() {
        Connection connection = null;
        String sql = "update user set name=? where id=?";
        PreparedStatement preparedStatement = null;

        try {
            //获取连接
            connection = JDBCUtils.getConnection();
            //创建preparedStatement对象
            preparedStatement = connection.prepareStatement(sql);
            //赋值
            preparedStatement.setString(1, "周星驰");
            preparedStatement.setString(2, "10");
            //执行
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.close(null, preparedStatement, connection);
        }
    }
}
