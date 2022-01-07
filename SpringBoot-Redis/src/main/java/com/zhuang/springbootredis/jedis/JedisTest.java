package com.zhuang.springbootredis.jedis;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

/**
 * @Classname JedisTest
 * @Description Jedis测试
 * @Date 2022/1/5 14:47
 * @Author by Zhuang
 */
@Slf4j
public class JedisTest {
    public static void main(String[] args) {
        // 创建对象
        Jedis jedis = new Jedis("101.43.21.132", 6379);
        //测试
        String vlaue = jedis.ping();
        log.info("vlaue = " + vlaue);
        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            log.info(key);
        }
        jedis.set("zk", "18");
        String zk = jedis.get("zk");
        log.info(zk);
        //设置多个key-value
        jedis.mset("k1", "v1", "k2", "v2");
        List<String> list = jedis.mget("k1", "k2");
        for (String s : list) {
            log.info(s);
        }
        //操作set
        jedis.sadd("name", "lucy");
        jedis.sadd("name", "jack");
        Set<String> smembers = jedis.smembers("name");
        for (String smember : smembers) {
            log.info(smember);
        }
        jedis.hset("users", "age", "20");
        String hget = jedis.hget("users", "age");
        log.info(hget);

        jedis.zadd("china", 100d, "shanghai");
        Set<String> set = jedis.zrange("china", 0, -1);
        log.info(set.toString());
        // 关闭jedis
        jedis.close();
    }
}
