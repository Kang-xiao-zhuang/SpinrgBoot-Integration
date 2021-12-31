package com.zhuang.springbootrabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Classname MyCallBack
 * @Description 自定义回调函数
 * @Date 2021/12/31 14:22
 * @Author by Zhuang
 */
@Slf4j
@Component
public class MyCallBack implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback, RabbitTemplate.ReturnCallback {

    /**
     * 注入
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 接口注入
     */
    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        // 二者选其一都可以
        //   rabbitTemplate.setReturnCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }

    /**
     * 交换机确认回调方法
     *
     * @param correlationData 保存回调消息的ID及相关信息
     * @param b               ack          true 交换机接收到消息
     * @param s               交换机接受失败 回调
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        String id = correlationData != null ? correlationData.getId() : "";
        if (b) {
            log.info("交换机已经收到Id为:{}的消息", id);
        } else {
            log.warn("交换机已经未收到Id为:{}的消息,原因:{}", id, s);
        }
    }

    /**
     * @param message    消息体
     * @param i          个数
     * @param replyText  原因
     * @param exchange   交换机
     * @param routingKey 路由
     */
    @Override
    public void returnedMessage(Message message, int i, String replyText, String exchange, String routingKey) {
        log.error("消息{},被交换机{}退回,退回原因:{},路由Key:{}", new String(message.getBody()), exchange, replyText, routingKey);
    }

    /**
     * @param returnedMessage 返回的消息
     */
    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        log.error("消息{},被交换机{}退回,退回原因:{},路由Key:{},错误码为{}",
                returnedMessage.getMessage(),
                returnedMessage.getExchange(),
                returnedMessage.getReplyText(),
                returnedMessage.getRoutingKey(),
                returnedMessage.getReplyCode());
    }
}
