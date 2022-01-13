package com.zhuang.springbootrabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;

/**
 * @Classname RabbitMqClient
 * @Description 自己研究
 * @Date 2021/1/13 11:27
 * @Author by Zhuang
 */
@Slf4j
@Configuration
public class RabbitMqClient {

    private RabbitAdmin rabbitAdmin;

    /**
     * 普通交换器
     */
    @Value("spring.rabbitmq.direct.exchange")
    public String directexchange;

    /**
     * DLX 死信队列交换器
     */
    @Value("spring.rabbitmq.dlx.exchange")
    public String dlxexchange;

    /**
     * TTL 过期时间
     */
    @Value("60000")
    public int ttlTime;

    /**
     * Dlx TTL 过期时间
     */
    @Value("60000")
    public int dlxTtlTime;

    /**
     * 死信交换机
     *
     * @return DirectExchange
     */
    @Bean
    public DirectExchange deadExchange() {
        return new DirectExchange(dlxexchange);
    }

    /**
     * 创建Exchange
     *
     * @param exchange AbstractExchange
     */
    public void addExchange(AbstractExchange exchange) {
        rabbitAdmin.declareExchange(exchange);
    }

    /**
     * 删除一个交换机
     *
     * @param exchangeName 交换机名称
     * @return boolean
     */
    public boolean deleteExchange(String exchangeName) {
        return rabbitAdmin.deleteExchange(exchangeName);
    }

    /**
     * 声明其名称自动命名的队列。它是用exclusive=true、autoDelete=true和 durable = false
     *
     * @return Queue
     */
    public Queue addQueue() {
        return rabbitAdmin.declareQueue();
    }


    /**
     * 创建一个指定的Queue
     *
     * @param queue Queue
     * @return queueName
     */
    public String addQueue(Queue queue) {
        return rabbitAdmin.declareQueue(queue);
    }


    /**
     * 删除一个队列
     *
     * @param queueName the name of the queue.
     * @param unused    true if the queue should be deleted only if not in use.
     * @param empty     true if the queue should be deleted only if empty.
     */
    public void deleteQueue(String queueName, boolean unused, boolean empty) {
        rabbitAdmin.deleteQueue(queueName, unused, empty);
    }

    /**
     * 删除一个队列
     *
     * @param queueName queueName
     * @return true if the queue existed and was deleted.
     */
    public boolean deleteQueue(String queueName) {
        return rabbitAdmin.deleteQueue(queueName);
    }

    /**
     * 绑定一个队列到一个匹配型交换器使用一个routingKey
     *
     * @param queue      Queue
     * @param exchange   TopicExchange
     * @param routingKey String
     */
    public void addBinding(Queue queue, TopicExchange exchange, String routingKey) {
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(routingKey);
        rabbitAdmin.declareBinding(binding);
    }

    /**
     * 绑定一个Exchange到一个匹配型Exchange 使用一个routingKey
     *
     * @param exchange      Exchange
     * @param topicExchange TopicExchange
     * @param routingKey    String
     */
    public void addBinding(Exchange exchange, TopicExchange topicExchange, String routingKey) {
        Binding binding = BindingBuilder.bind(exchange).to(topicExchange).with(routingKey);
        rabbitAdmin.declareBinding(binding);
    }

    /**
     * 去掉一个binding
     *
     * @param binding Binding
     */
    public void removeBinding(Binding binding) {
        rabbitAdmin.removeBinding(binding);
    }

    /**
     * 创建交换器
     *
     * @param exchangeName String
     * @return DirectExchange
     */
    public DirectExchange createExchange(String exchangeName) {
        return new DirectExchange(exchangeName, true, false);
    }
}
