package com.zhuang.springbootrabbitmq.ttl;

import com.rabbitmq.client.*;
import com.zhuang.springbootrabbitmq.utils.RabbitMQUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

/**
 * @Classname DeadConsumer
 * @Description 消费死信队列消费者
 * @Date 2021/12/29 14:47
 * @Author by Zhuang
 */
@Slf4j
public class DeadConsumer {
    public static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQUtils.getChannel("101.43.21.132", 5672, "admin", "admin", "/");

        channel.basicConsume(DEAD_QUEUE, true, (s, delivery) -> log.info("DeadConsumer接受消息" + new String(delivery.getBody(), StandardCharsets.UTF_8)), new CancelCallback() {
            @Override
            public void handle(String s) throws IOException {
                log.warn("接受失败");
            }
        });
    }
}
