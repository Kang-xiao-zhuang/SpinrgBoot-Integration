package com.zhuang.springbootrabbitmq.all;

import com.rabbitmq.client.Channel;
import com.zhuang.springbootrabbitmq.utils.RabbitMQUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @Classname AllProducer
 * @Description Topic匹配案例
 * @Date 2021/12/28 15:49
 * @Author by Zhuang
 */
@Slf4j
public class AllProducer {
    public static final String EXCHANGE_NAME = "topic_logs";
    protected static final String[] QUEUE_NAME = {"Q1", "Q2"};

    public static void main(String[] args) throws IOException, TimeoutException {
        try (Channel channel = RabbitMQUtils.getChannel("101.43.21.132", 5672, "admin", "admin", "/");) {
            HashMap<String, String> bindingKeyMap = new HashMap<>(20);
            bindingKeyMap.put("quick.orange.rabbit", "被队列 Q1Q2 接收到");
            bindingKeyMap.put("lazy.orange.elephant", "被队列 Q1Q2 接收到");
            bindingKeyMap.put("quick.orange.fox", "被队列 Q1 接收到");
            bindingKeyMap.put("lazy.brown.fox", "被队列 Q2 接收到");
            bindingKeyMap.put("lazy.pink.rabbit", "虽然满足两个绑定但只被队列 Q2 接收一次");
            bindingKeyMap.put("quick.brown.fox", "不匹配任何绑定不会被任何队列接收到会被丢弃");
            bindingKeyMap.put("quick.orange.male.rabbit", "是四个单词不匹配任何绑定会被丢弃");
            bindingKeyMap.put("lazy.orange.male.rabbit", "是四个单词但匹配 Q2");
            for (Map.Entry<String, String> bindingKeyEntry : bindingKeyMap.entrySet()) {
                String key = bindingKeyEntry.getKey();
                String value = bindingKeyEntry.getValue();
                channel.basicPublish(EXCHANGE_NAME, key, null, value.getBytes(StandardCharsets.UTF_8));
            }
        }
    }
}
