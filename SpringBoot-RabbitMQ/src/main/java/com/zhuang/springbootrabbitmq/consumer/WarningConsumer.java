package com.zhuang.springbootrabbitmq.consumer;

import com.zhuang.springbootrabbitmq.config.ConfirmConfig;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * @Classname WarningConsumer
 * @Description 报警消费者
 * @Date 2022/1/1 7:39
 * @Author by dell
 */
@Component
@Slf4j
public class WarningConsumer {
    @RabbitListener(queues = ConfirmConfig.WARNING_QUEUE_NAME)
    public void receiverWarningMsg(Message message) {
        String msg = new String(message.getBody());
        log.error("报警发现不可路由消息:{}", msg);
    }
}
