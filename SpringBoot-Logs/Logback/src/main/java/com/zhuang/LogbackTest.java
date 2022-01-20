package com.zhuang;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackTest {
    // 声明日志对象
    public static final Logger LOGGER = LoggerFactory.getLogger(LogbackTest.class);

    @Test
    public void testQuick() {
        //打印日志信息
        LOGGER.error("error");
        LOGGER.warn("warn");
        LOGGER.info("info");
        LOGGER.debug("debug");
        LOGGER.trace("trace");
        // 使用占位符输出日志信息
        String name = "jack";
        Integer age = 18;
        LOGGER.info("用户：{},{}", name, age);
    }
}
