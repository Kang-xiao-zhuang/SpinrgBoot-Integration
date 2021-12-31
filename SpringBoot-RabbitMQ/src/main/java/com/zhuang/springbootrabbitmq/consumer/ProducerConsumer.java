package com.zhuang.springbootrabbitmq.consumer;

import com.zhuang.springbootrabbitmq.config.ConfirmConfig;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * @Classname ProducerConsumer
 * @Description 发布订阅高级消费者
 * @Date 2021/12/31 11:42
 * @Author by Zhuang
 */
@Component
@Slf4j
public class ProducerConsumer {

    @RabbitListener(queues = ConfirmConfig.CONFIRM_QUEUE_NAME)
    public void receiveConfigMessage(Message message) {
        String msg = new String(message.getBody());
        log.info("接受到的队列confirm.queue消息:{}", msg);
    }
}
