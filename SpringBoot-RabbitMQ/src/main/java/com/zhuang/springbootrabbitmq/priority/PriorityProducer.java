package com.zhuang.springbootrabbitmq.priority;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.zhuang.springbootrabbitmq.utils.RabbitMQUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

/**
 * @Classname PriorityProducer
 * @Description 优先级生产者
 * @Date 2022/1/1 8:01
 * @Author by dell
 */
@Slf4j
public class PriorityProducer {
    public static final String QUEUE_NAME = "queue_priority";

    public static void main(String[] args) throws TimeoutException {

        // 创建连接工程
        try (Channel channel = RabbitMQUtils.getChannel("101.43.21.132", 5672, "admin", "admin", "/")) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("x-max-priority", 10);
            //通过连接获得通道Channel
            //通过交换机创建 声明队列 绑定关系 路由key 发送消息 和接收消息
            /*
            队列名称
            是否要持久化
            排他性
            是否自动删除
            携带附属参数
             */
            channel.queueDeclare(QUEUE_NAME, true, false, false, map);
            for (int i = 0; i < 15; i++) {
                String message = "hello kang xiao zhuang" + i;
                if (i == 5) {
                    AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().priority(5).build();
                    //发送消息给队列
                    channel.basicPublish("", QUEUE_NAME, properties, message.getBytes());
                } else {
                    //发送消息给队列
                    channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                }
            }
            log.info("消息发送成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
