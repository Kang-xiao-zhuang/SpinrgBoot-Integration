package com.zhuang.springbootredis.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SchedulerTask {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Scheduled(cron = "*/5 * * * * ?")
    private void processBucket() {
        //一次性消费两个
        //log.info("正在消费。。。。。。");
        String redisKey = "qpsList";
        //redisTemplate.opsForList().trim(redisKey, 2, -1);
    }

    @Scheduled(cron = "*/5 * * * * ?")
    private void processLinPaiBucket() {
        //一次性生产两个
        log.info("正在消费。。。。。。");
        for (int i = 0; i < 2; i++) {
            String redisKey = "lingpaitong";
            redisTemplate.opsForList().rightPush(redisKey, String.valueOf(i));
        }
    }
}
