package com.zhuang.springbootrabbitmq.all;

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
 * @Classname AllConsumer2
 * @Description 所有交换机
 * @Date 2021/12/28 15:35
 * @Author by Zhuang
 */
@Slf4j
public class AllConsumer2 {
    public static final String EXCHANGE_NAME = "topic_logs";
    protected static final String[] QUEUE_NAME = {"Q1", "Q2"};

    public static void main(String[] args) {
        try (Channel channel = RabbitMQUtils.getChannel("101.43.21.132", 5672, "admin", "admin", "/");) {
            String type = "topic";
            channel.exchangeDeclare(EXCHANGE_NAME, type);
            channel.queueDeclare(QUEUE_NAME[1], false, false, false, null);
            channel.queueBind(QUEUE_NAME[1], EXCHANGE_NAME, "*.orange.*");

            channel.basicConsume(QUEUE_NAME[1], true, new DeliverCallback() {
                @Override
                public void handle(String s, Delivery delivery) throws IOException {
                    String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                    log.info("接收到的消息" + message);
                    log.info("队列名->" + QUEUE_NAME[1] + "绑定键->" + delivery.getEnvelope().getRoutingKey());
                }
            }, new CancelCallback() {
                @Override
                public void handle(String s) throws IOException {
                    log.warn("接受失败...");
                }
            });
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
