package com.zhuang.springbootrabbitmq.topic;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import com.zhuang.springbootrabbitmq.utils.RabbitMQUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @Classname TopicConsumer
 * @Description 发布订阅消费者
 * @Date 2021/12/28 13:18
 * @Author by Zhuang
 */
@Slf4j
public class TopicConsumer {
    public static final String EXCHANGE_NAME = "topic_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQUtils.getChannel("101.43.21.132", 5672, "admin", "admin", "/");
        String type = "fanout";
        channel.exchangeDeclare(EXCHANGE_NAME, type);
        /*
         * 生成一个临时的队列 队列的名称是随机的
         * 当消费者断开和该队列的连接时 队列自动删除
         */
        String queueName = channel.queueDeclare().getQueue();
        //把该临时队列绑定我们的 exchange 其中 routingkey(也称之为 binding key)为空字符串
        channel.queueBind(queueName, EXCHANGE_NAME, "");
        log.info("等待接收消息,把接收到的消息打印在屏幕........... ");
        channel.basicConsume(queueName, true, new DeliverCallback() {
            @Override
            public void handle(String s, Delivery delivery) throws IOException {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                log.info("接收到的消息" + message);
            }
        }, new CancelCallback() {
            @Override
            public void handle(String s) throws IOException {
                log.warn("接受失败...");
            }
        });
    }
}
