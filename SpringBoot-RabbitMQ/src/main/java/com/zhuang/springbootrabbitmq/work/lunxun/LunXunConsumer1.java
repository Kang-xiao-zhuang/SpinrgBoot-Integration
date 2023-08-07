package com.zhuang.springbootrabbitmq.work.lunxun;

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
 * @Classname LunXunConsumer1
 * @Description 轮询消费者1
 * @Date 2021/12/28 15:11
 * @Author by Zhuang
 */
@Slf4j
public class LunXunConsumer1 {
    public static final String QUEUE_NAME = "queue_work";

    public static void main(String[] args) throws TimeoutException {
            // 创建连接工程
        try (Channel channel = RabbitMQUtils.getChannel("101.43.21.132", 5672, "admin", "admin", "/")) {
            //通过连接获得通道Channel
            //通过交换机创建 声明队列 绑定关系 路由key 发送消息 和接收消息
            channel.basicConsume(QUEUE_NAME, true, (s, delivery) -> log.info("Consumer1号接收消息" + new String(delivery.getBody(), StandardCharsets.UTF_8)), new CancelCallback() {
                @Override
                public void handle(String s) {
                    log.warn("接受失败...");
                }
            });
            log.info("Consumer1号开始接收消息");
            System.in.read();
            log.info("消息发送成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
