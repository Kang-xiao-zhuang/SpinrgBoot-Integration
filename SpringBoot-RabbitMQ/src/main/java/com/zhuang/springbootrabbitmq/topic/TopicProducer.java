package com.zhuang.springbootrabbitmq.topic;

import com.rabbitmq.client.Channel;
import com.zhuang.springbootrabbitmq.utils.RabbitMQUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @Classname TopicProducer
 * @Description 发布订阅生产者
 * @Date 2021/12/28 13:17
 * @Author by Zhuang
 */
@Slf4j
public class TopicProducer {
    public static final String EXCHANGE_NAME = "topic_exchange";

    public static void main(String[] args) {
        try (Channel channel = RabbitMQUtils.getChannel("101.43.21.132", 5672, "admin", "admin", "/")) {
            /*
             * 声明一个 exchange
             * 1.exchange 的名称
             * 2.exchange 的类型
             */
            String type = "fanout";
            channel.exchangeDeclare(EXCHANGE_NAME, type);
            Scanner sc = new Scanner(System.in);
            log.info("请输入信息");
            while (sc.hasNext()) {
                String message = sc.nextLine();
                channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes(StandardCharsets.UTF_8));
                log.info("生产者发出消息" + message);
            }
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
