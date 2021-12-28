package com.zhuang.springbootrabbitmq.direct;

import com.rabbitmq.client.*;
import com.zhuang.springbootrabbitmq.utils.RabbitMQUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @Classname DirectConsumer2
 * @Description 直接交换机2
 * @Date 2021/12/28 14:57
 * @Author by Zhuang
 */
@Slf4j
public class DirectConsumer2 {
    public static final String EXCHANGE_NAME = "topic_direct";
    public static final String QUEUE_NAME = "direct_disk";
    protected static final String[] ROUTING_KEY = {"info", "warning", "error"};

    public static void main(String[] args) throws IOException, TimeoutException {
        try (Channel channel = RabbitMQUtils.getChannel("101.43.21.132", 5672, "admin", "admin", "/");) {
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY[2]);

            channel.basicConsume(QUEUE_NAME, true, new DeliverCallback() {
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
}
