package com.zhuang.springbootrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname ConfirmConfig
 * @Description 发布确认高级
 * @Date 2021/12/31 11:27
 * @Author by Zhuang
 */
@Configuration
public class ConfirmConfig {
    //确认交换机
    public static final String CONFIRM_EXCHANGE_NAME = "confirm.exchange";
    //确认队列
    public static final String CONFIRM_QUEUE_NAME = "confirm.queue";
    //路由
    public static final String ROUTING_KEY = "key1";
    //备份交换机
    public static final String BACKUP_EXCHANGE_NAME = "backup.exchange";
    //备份队列
    public static final String BACKUP_QUEUE_NAME = "backup.queue";
    //报警队列
    public static final String WARNING_QUEUE_NAME = "warning.queue";


    /**
     * 声明业务 Exchange
     *
     * @return DirectExchange
     */
    @Bean("confirmExchange")
    public DirectExchange confirmExchange() {
//        return new DirectExchange(CONFIRM_EXCHANGE_NAME);
        return ExchangeBuilder.directExchange(CONFIRM_EXCHANGE_NAME).durable(true).withArgument("alternate-exchange", BACKUP_EXCHANGE_NAME).build();
    }

    /**
     * 声明确认队列
     *
     * @return Queue
     */
    @Bean("confirmQueue")
    public Queue confirmQueue() {
        return QueueBuilder.durable(CONFIRM_QUEUE_NAME).build();
    }

    /**
     * 声明确认队列绑定关系
     *
     * @param queue    队列
     * @param exchange 交换机
     * @return Binding
     */
    @Bean
    public Binding queueBindingExchange(@Qualifier("confirmQueue") Queue queue, @Qualifier("confirmExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    /**
     * 备份交换机
     *
     * @return DirectExchange
     */
    @Bean("backupExchange")
    public FanoutExchange backupExchange() {
        return new FanoutExchange(BACKUP_EXCHANGE_NAME);
    }

    /**
     * 备份队列
     *
     * @return Queue
     */
    @Bean("backupQueue")
    public Queue backupQueue() {
        return QueueBuilder.durable(BACKUP_QUEUE_NAME).build();
    }

    /**
     * 报警队列
     *
     * @return Queue
     */
    @Bean("warningQueue")
    public Queue warningQueue() {
        return QueueBuilder.durable(WARNING_QUEUE_NAME).build();
    }

    /**
     * 声明备份队列绑定关系
     *
     * @param queue    队列
     * @param exchange 交换机
     * @return Binding
     */
    @Bean
    public Binding backupQueueBindingExchange(@Qualifier("backupQueue") Queue queue, @Qualifier("backupExchange") FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    /**
     * 声明报警队列绑定关系
     *
     * @param queue    队列
     * @param exchange 交换机
     * @return Binding
     */
    @Bean
    public Binding warningQueueBindingExchange(@Qualifier("warningQueue") Queue queue, @Qualifier("backupExchange") FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }
}
