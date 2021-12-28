package com.zhuang.springbootrabbitmq.direct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.zhuang.springbootrabbitmq.utils.RabbitMQUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

/**
 * @Classname DirectProducer
 * @Description 直接生产者
 * @Date 2021/12/28 15:11
 * @Author by Zhuang
 */
@Slf4j
public class DirectProducer {
    public static final String EXCHANGE_NAME = "topic_direct";
    protected static final String[] ROUTING_KEY = {"info", "warning", "error"};

    public static void main(String[] args) throws IOException, TimeoutException {
        try (Channel channel = RabbitMQUtils.getChannel("101.43.21.132", 5672, "admin", "admin", "/")) {
            Scanner sc = new Scanner(System.in);
            log.info("请输入信息");
            while (sc.hasNext()) {
                String message = sc.nextLine();
                channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY[2], null, message.getBytes(StandardCharsets.UTF_8));
                log.info("生产者发出消息" + message);
            }
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
