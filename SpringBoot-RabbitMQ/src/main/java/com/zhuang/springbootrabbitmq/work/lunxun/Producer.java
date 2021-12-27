package com.zhuang.springbootrabbitmq.work.lunxun;

import com.rabbitmq.client.Channel;
import com.zhuang.springbootrabbitmq.utils.RabbitMQUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Zhuang
 */
@Slf4j
public class Producer {
    public static final String QUEUE_NAME = "queue_work";

    public static void main(String[] args) {
        try (Channel channel = RabbitMQUtils.getChannel("101.43.21.132", 5672, "admin", "admin", "/")) {
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            for (int i = 0; i < 20; i++) {
                String message = "hello kang xiao zhuang-->轮询模式" + i;
                //发送消息给队列
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            }
            log.info("消息发送成功");
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
