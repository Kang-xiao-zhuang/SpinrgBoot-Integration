package com.zhuang.springbootrabbitmq.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.zhuang.springbootrabbitmq.utils.RabbitMQUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeoutException;

/**
 * @Classname ConfirmMessage
 * @Description 消息发布确认
 * @Date 2021/12/28 10:27
 * @Author by Zhuang
 */
@Slf4j
public class ConfirmMessage {
    public static final int MESSAGE_COUNT = 1000;
    public static final String IP_ADDRESS = "101.43.21.132";
    public static final int PORT = 5672;
    public static final String USER = "admin";
    public static final String PASSWORD = "admin";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConfirmMessage.publishMessageIndividually();
        ConfirmMessage.publishMessageBatch();
        ConfirmMessage.publishMessageAsync();
    }

    /**
     * 单个发布确认
     */
    public static void publishMessageIndividually() throws IOException, TimeoutException, InterruptedException {
        Channel channel = RabbitMQUtils.getChannel(IP_ADDRESS, PORT, USER, PASSWORD, "/");
        // 队列声明
        String queuename = UUID.randomUUID().toString();

        channel.queueDeclare(queuename, true, false, false, null);
        channel.confirmSelect();

        long begin = System.currentTimeMillis();
        //批量发消息
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = "消息" + i;
            channel.basicPublish("", queuename, null, message.getBytes());
            // 单个消息发布马上确认
            boolean flag = channel.waitForConfirms();
            if (flag) {
                log.info("消息发送成功");
            }
        }
        long end = System.currentTimeMillis();

        log.info("发布" + MESSAGE_COUNT + "个单个发布确认消息,耗时" + (end - begin) + "ms");
    }

    /**
     * 批量发布确认
     */
    public static void publishMessageBatch() throws IOException, TimeoutException, InterruptedException {
        Channel channel = RabbitMQUtils.getChannel(IP_ADDRESS, PORT, USER, PASSWORD, "/");
        // 队列声明
        String queuename = UUID.randomUUID().toString();

        channel.queueDeclare(queuename, true, false, false, null);
        channel.confirmSelect();

        long begin = System.currentTimeMillis();
        //批量确认消息大小
        int bathcSize = 100;
        //批量发消息
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = "消息" + i;
            channel.basicPublish("", queuename, null, message.getBytes());
            // 单个消息发布马上确认
            if (i % bathcSize == 0) {
                log.info("消息发送成功");
                channel.waitForConfirms();
            }
        }
        long end = System.currentTimeMillis();

        log.info("发布" + MESSAGE_COUNT + "个单个发布确认消息,耗时" + (end - begin) + "ms");
    }

    /**
     * 异步确认消息
     */
    public static void publishMessageAsync() throws IOException, TimeoutException {
        Channel channel = RabbitMQUtils.getChannel(IP_ADDRESS, PORT, USER, PASSWORD, "/");
        // 队列声明
        String queuename = UUID.randomUUID().toString();

        channel.queueDeclare(queuename, true, false, false, null);
        channel.confirmSelect();

        long begin = System.currentTimeMillis();
        // 线程安全有序的一个哈希表 适用于高并发的情况下
        ConcurrentSkipListMap<Long, String> outstandingConfirms = new ConcurrentSkipListMap<>();
        channel.addConfirmListener(new ConfirmCallback() {
            @Override
            public void handle(long l, boolean b) throws IOException {
                //消息成功回调函数
                if (b) {
                    ConcurrentNavigableMap<Long, String> confirm = outstandingConfirms.headMap(l);
                    confirm.clear();
                } else {
                    outstandingConfirms.remove(l);
                }
                log.info("确认的消息" + l);
            }
        }, new ConfirmCallback() {
            @Override
            public void handle(long l, boolean b) throws IOException {
                //消息失败回调函数
                String message = outstandingConfirms.get(l);
                log.warn("未确认的消息" + message);
            }
        });
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = "消息" + i;
            channel.basicPublish("", queuename, null, message.getBytes());
            outstandingConfirms.put(channel.getNextPublishSeqNo(), message);
        }

        long end = System.currentTimeMillis();

        log.info("发布" + MESSAGE_COUNT + "个异步发布确认消息,耗时" + (end - begin) + "ms");
    }
}
