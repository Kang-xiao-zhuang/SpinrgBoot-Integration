package com.zhuang.springbootlogs;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
class SpringBootLogsApplicationTests {

    @Test
    void contextLoads() {
    }

    //记录器
    public static final Logger LOGGER = LoggerFactory.getLogger(SpringBootLogsApplicationTests.class);

    @Test
    void test() {
    // 打印日志信息
        LOGGER.error("error");
        LOGGER.warn("warn");
        LOGGER.info("info"); // 默认日志级别
        LOGGER.debug("debug");
        LOGGER.trace("trace");
    }
}
