package com.zhuang.springbootredis.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

@RestController
@RequestMapping("/redis")
@Slf4j
public class RedisTestController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private Jedis jedis;

    @GetMapping
    public String testRedis() {
        redisTemplate.opsForValue().set("zk", "18");
        String name = redisTemplate.opsForValue().get("zk");
        log.info(name);
        return name;
    }

    @GetMapping("/setnx/{key}/{val}")
    public boolean setnx(@PathVariable String key, @PathVariable String val) {
        Long setnx = jedis.setnx(key, val);
        System.out.println("setnx = " + setnx);
        return true;
    }
}
