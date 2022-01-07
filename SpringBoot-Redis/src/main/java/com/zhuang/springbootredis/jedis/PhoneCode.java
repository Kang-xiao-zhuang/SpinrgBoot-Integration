package com.zhuang.springbootredis.jedis;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.util.Random;

/**
 * @Classname PhoneCode
 * @Description PhoneCode小案例
 * @Date 2022/1/7 14:47
 * @Author by Zhuang
 */
@Slf4j
public class PhoneCode {
    /**
     * 电话号码
     */
    public static final String PHONE = "18014000830";

    public static final long TTLTIME = 24 * 60 * 60;

    public static void main(String[] args) {
        verifyCode(PHONE);
        checkCode(PHONE, "582088");
    }

    /**
     * 生成验证码
     *
     * @return 验证码
     */
    public static String getCode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int code = new Random().nextInt(10);
            sb.append(code);
        }
        return sb.toString();
    }

    /**
     * 每个手机只能发送三次验证码，验证码发送到Redis，设置时间
     *
     * @param phone 字符串
     */
    public static void verifyCode(String phone) {
        // 连接redis
        Jedis jedis = new Jedis("101.43.21.132", 6379);
        // 拼接key
        // 手机验证次数key
        String countKey = "Code" + phone + ":count";
        // 验证码key
        String codeKey = "Code" + phone + ":code";
        String count = jedis.get(countKey);
        if (count == null) {
            //没有就发送
            jedis.setex(countKey, TTLTIME, "1");
        } else if (Integer.parseInt(count) <= 2) {
            // 发送次数+1
            jedis.incr(countKey);
        } else if (Integer.parseInt(count) > 3) {
            log.error("今天发送次数超过三次");
            jedis.close();
        }
        String vcode = getCode();
        jedis.setex(codeKey, TTLTIME, vcode);
        jedis.close();
    }

    /**
     * 校验验证码
     *
     * @param phone 字符串
     * @param code  验证码
     */
    public static void checkCode(String phone, String code) {
        // 连接redis
        Jedis jedis = new Jedis("101.43.21.132", 6379);
        // 验证码key
        String codeKey = "Code" + phone + ":code";
        String redisCode = jedis.get(codeKey);
        if (redisCode.equals(code)) {
            log.info("==============成功==============");
        } else {
            log.error("==============失败==============");
        }
        jedis.close();
    }
}
