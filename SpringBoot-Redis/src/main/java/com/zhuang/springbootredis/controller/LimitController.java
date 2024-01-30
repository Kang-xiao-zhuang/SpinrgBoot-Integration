package com.zhuang.springbootredis.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicInteger;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/limit")
@Slf4j
public class LimitController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    // 固定时间窗口算法
    @GetMapping("/start")
    public Map<String, Object> start(@RequestParam Map<String, Object> paramMap) {
        //根据前端传递的qps上线
        int times = 100;
        if (paramMap.containsKey("times")) {
            times = Integer.parseInt(paramMap.get("times").toString());
        }
        String redisKey = "redisQps";
        RedisAtomicInteger redisAtomicInteger = new RedisAtomicInteger(redisKey, Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        int no = redisAtomicInteger.getAndIncrement();
        //设置时间固定时间窗口长度 1S
        if (no == 0) {
            redisAtomicInteger.expire(1, TimeUnit.SECONDS);
        }
        //判断是否超限  time=2 表示qps=3
        log.info("no值->{}", no);
        if (no > times) {
            throw new RuntimeException("qps refuse request");
        }
        log.info("times值->{}", times);
        //返回成功告知
        Map<String, Object> map = new HashMap<>();
        map.put("success", "success");
        return map;
    }

    // 滑动时间窗口算法
    @GetMapping("/startList")
    public Map<String, Object> startList(@RequestParam Map<String, Object> paramMap) {
        String redisKey = "qpsZset";
        Integer times = 100;
        if (paramMap.containsKey("times")) {
            times = Integer.valueOf(paramMap.get("times").toString());
        }
        long currentTimeMillis = System.currentTimeMillis();
        long interMills = 10000L;
        Long count = redisTemplate.opsForZSet().count(redisKey, currentTimeMillis - interMills, currentTimeMillis);
        // 检查QPS（Queries Per Second）是否超过限制
        /**
         * 使用System.currentTimeMillis()获取当前时间戳。
         * 设置一个时间间隔interMills为1000毫秒（即1秒）。
         * 使用redisTemplate.opsForZSet().count(redisKey, currentTimeMillis - interMills, currentTimeMillis)查询过去1秒内Redis ZSet中指定键（redisKey）的数量。这个数量会被赋值给count变量。
         * 如果count的值大于times，则抛出一个运行时异常，表示QPS超过限制
         */
        if (count > times) {
            throw new RuntimeException("qps refuse request");
        }
        redisTemplate.opsForZSet().add(redisKey, UUID.randomUUID().toString(), currentTimeMillis);
        Map<String, Object> map = new HashMap<>();
        map.put("success", "success");
        return map;
    }

    // 漏桶算法
    @GetMapping("/startLoutong")
    public Map<String, Object> startLoutong(@RequestParam Map<String, Object> paramMap) {
        String redisKey = "qpsList";
        Integer times = 100;
        if (paramMap.containsKey("times")) {
            times = Integer.valueOf(paramMap.get("times").toString());
        }
        Long size = redisTemplate.opsForList().size(redisKey);
        // 检查队列长度是否超过限制
        if (size >= times) {
            throw new RuntimeException("qps refuse request");
        }
        // 添加请求到队列Redis列表的右侧。
        Long aLong = redisTemplate.opsForList().rightPush(redisKey, String.valueOf(paramMap));
        if (aLong > times) {
            //为了防止并发场景。这里添加完成之后也要验证。  即使这样本段代码在高并发也有问题。此处演示作用
            // 修剪Redis列表，使其长度为times
            redisTemplate.opsForList().trim(redisKey, 0, times - 1);
            throw new RuntimeException("qps refuse request");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("success", "success");
        return map;
    }

    // 令牌桶算法
    @GetMapping("/startLingpaitong")
    public Map<String, Object> startLingpaitong(Map<String, Object> paramMap) {
        String redisKey = "lingpaitong";
        String token = redisTemplate.opsForList().leftPop(redisKey);
        //正常情况需要验证是否合法，防止篡改
        if (StringUtils.isEmpty(token)) {
            throw new RuntimeException("令牌桶拒绝");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("success", "success");
        return map;
    }

}
