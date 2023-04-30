package com.zhuang.springbootredis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import redis.clients.jedis.Jedis;

@SpringBootTest
class SpringBootRedisApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void test() {
        // 创建对象
        Jedis jedis = new Jedis("192.168.18.129", 6379);
        //测试
        String vlaue = jedis.ping();
        System.out.println("vlaue = " + vlaue);
    }

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void testRedis() {
        redisTemplate.opsForValue().set("zk", "18");
        Object name = redisTemplate.opsForValue().get("zk");
        System.out.println("name = " + name);
    }
}
