package com.zhuang.springbootrabbitmq.simple;

import com.rabbitmq.client.*;
import com.zhuang.springbootrabbitmq.utils.RabbitMQUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author Zhuang
 */
@Slf4j
public class Consumer {
    public static final String QUEUE_NAME = "queue_test";

    public static void main(String[] args) throws TimeoutException {
        // 创建连接工程
        try (Channel channel = RabbitMQUtils.getChannel("101.43.21.132", 5672, "admin", "admin", "/")) {
            //通过连接获得通道Channel
            //通过交换机创建 声明队列 绑定关系 路由key 发送消息 和接收消息
            channel.basicConsume(QUEUE_NAME, true, new DeliverCallback() {
                @Override
                public void handle(String s, Delivery delivery) throws IOException {
                    log.info("收到的消息是" + new String(delivery.getBody(), StandardCharsets.UTF_8));
                }
            }, new CancelCallback() {
                @Override
                public void handle(String s) throws IOException {
                    log.warn("接受失败...");
                }
            });
            System.in.read();
            log.info("消息发送成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
