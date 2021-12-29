package com.zhuang.springbootrabbitmq.ttl;

import com.rabbitmq.client.*;
import com.zhuang.springbootrabbitmq.utils.RabbitMQUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

/**
 * @Classname NormalConsumer
 * @Description 正常队列消费者
 * @Date 2021/12/29 14:47
 * @Author by Zhuang
 */
@Slf4j
public class NormalConsumer {
    public static final String NORMAL_EXCHANGE = "normal_exchange";
    public static final String DEAD_EXCHANGE = "dead_exchange";
    public static final String NORMAL_QUEUE = "normal_queue";
    public static final String DEAD_QUEUE = "dead_queue";


    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQUtils.getChannel("101.43.21.132", 5672, "admin", "admin", "/");
        // 声明死信和普通交换机 类型为Direct
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);
        HashMap<String, Object> map = new HashMap<>(5);
        // 过期时间
        // 正常队列设置死信交换机
        map.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        // 设置死信RoutingKey
        map.put("x-dead-letter-routing-key", "lisi");
        // 消息最大长度的限制
//        map.put("x-max-length",6);
        //声明普通队列
        channel.queueDeclare(NORMAL_QUEUE, false, false, false, map);
        //声明死信队列
        channel.queueDeclare(DEAD_QUEUE, false, false, false, null);
        // 绑定普通的交换机与普通的队列
        channel.queueBind(NORMAL_QUEUE, NORMAL_EXCHANGE, "zhangsan");
        // 绑定死信的交换机与死信的队列
        channel.queueBind(DEAD_QUEUE, DEAD_EXCHANGE, "lisi");

        channel.basicConsume(NORMAL_QUEUE, false, new DeliverCallback() {
            @Override
            public void handle(String s, Delivery delivery) throws IOException {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                if ("info5".equals(message)) {
                    //requeue 设置为 false 代表拒绝重新入队 该队列如果配置了死信交换机将发送到死信队列中
                    log.info("NormalConsumer 接收到消息" + message + "并拒绝签收该消息");
                    channel.basicReject(delivery.getEnvelope().getDeliveryTag(), false);
                }
                log.info("NormalConsumer接受消息" + message);
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        }, new CancelCallback() {
            @Override
            public void handle(String s) throws IOException {
                log.warn("接受失败");
            }
        });
    }
}
