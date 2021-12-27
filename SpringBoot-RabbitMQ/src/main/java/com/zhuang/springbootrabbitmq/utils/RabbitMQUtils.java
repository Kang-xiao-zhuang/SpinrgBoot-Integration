package com.zhuang.springbootrabbitmq.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Zhuang
 */
public class RabbitMQUtils {
    /**
     * 创建RabbitMQ连接工具
     *
     * @param host        IP地址
     * @param port        端口
     * @param username    用户名
     * @param password    密码
     * @param virtualhost 虚拟地址
     * @return ConnectionFactory
     */
    public static Channel getChannel(String host, Integer port, String username, String password, String virtualhost) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualhost);
        Connection connection = connectionFactory.newConnection();
        return connection.createChannel();
    }

    /**
     * 构造方法
     */
    private RabbitMQUtils() {

    }
}
