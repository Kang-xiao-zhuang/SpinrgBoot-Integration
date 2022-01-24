package com.zhuang.springbootnio.jdbc;


import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * @Classname JDBCUtils
 * @Description JDBCUtils工具类
 * @Date 2021/6/16 20:48
 * @Created by dell
 */

public class JDBCUtils {
    /**
     * 定义4个相关属性
     */
    private static String user;
    private static String password;
    private static String url;
    private static String driver;

    static {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("src\\jdbc.properties"));
            user = properties.getProperty("user");
            password = properties.getProperty("password");
            url = properties.getProperty("url");
            driver = properties.getProperty("driver");
        } catch (Exception e) {
            // 将编译异常转成运行异常
            // 可以选择捕获异常或者选择默认处理异常
            throw new RuntimeException(e);
        }
    }

    // 连接数据库 返回连接
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //关闭相关资源
        /*
        1. ResultSet 结果集
        2. Statement 或者 PreparedStatement
        3. Connection
        4. 如果需要关闭资源，就传入对象，否则传入 null
        */
    public static void close(ResultSet set, Statement statement, Connection connection) {
        //判断是否为 null
        try {
            if (set != null) {
                set.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            //将编译异常转成运行异常抛出
            throw new RuntimeException(e);
        }
    }
}
