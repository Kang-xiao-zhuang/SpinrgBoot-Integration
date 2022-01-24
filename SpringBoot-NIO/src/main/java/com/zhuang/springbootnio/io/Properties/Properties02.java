package com.zhuang.springbootnio.io.Properties;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * @Classname Properties02
 * @Description Properties02
 * @Date 2021/5/31 16:29
 * @Created by dell
 */

public class Properties02 {
    public static void main(String[] args) throws IOException {
        //使用 Properties 类来读取 mysql.properties 文件
//1. 创建 Properties 对象
        Properties properties = new Properties();
//2. 加载指定配置文件
        properties.load(new FileReader("f:\\mysql.properties"));
//3. 把 k-v 显示控制台
        properties.list(System.out);
//4. 根据 key 获取对应的值
        String user = properties.getProperty("user");
        String pwd = properties.getProperty("pwd");
        String ip = properties.getProperty("ip");
        System.out.println("用户名=" + user);
        System.out.println("密码是=" + pwd);
        System.out.println("IP是=" + ip);
    }
}
