package com.zhuang.springbootrabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.zhuang.springbootrabbitmq.utils.RabbitMQUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Classname SimpleProducer
 * @Description 简单生产者
 * @Date 2021/12/28 15:11
 * @Author by Zhuang
 */
@Slf4j
public class SimpleProducer {
    public static final String QUEUE_NAME = "queue_test";

    public static void main(String[] args) throws TimeoutException {
        // 创建连接工程
        try (Channel channel = RabbitMQUtils.getChannel("101.43.21.132", 5672, "admin", "admin", "/")) {
            //通过连接获得通道Channel
            //通过交换机创建 声明队列 绑定关系 路由key 发送消息 和接收消息
            /*
            队列名称
            是否要持久化
            排他性
            是否自动删除
            携带附属参数
             */
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "hello kang xiao zhuang";
            //发送消息给队列
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            log.info("消息发送成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
