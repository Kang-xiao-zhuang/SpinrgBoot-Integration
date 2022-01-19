package com.zhuang.springbootlogs;

import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.*;

@SpringBootTest
class SpringBootLogsApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testQuick() {
        // 获取日志记录对象
        Logger logger = Logger.getLogger("com.zhuang.springbootlogs.SpringBootLogsApplicationTests");
        // 日志记录输出
        logger.info("hello zk");
        // 通过占位符 方式输出变量值
        String name = "zk";
        int age = 18;
        logger.log(Level.INFO, "用户信息：{0},{1}", new Object[]{name, age});
    }

    @Test
    void testLogLevel() throws IOException {
        //日志级别
        Logger logger = Logger.getLogger("com.zhuang.springbootlogs.SpringBootLogsApplicationTests");
        // 关闭系统默认设置
        logger.setUseParentHandlers(false);
        // 自定义日志级别
        ConsoleHandler consoleHandler = new ConsoleHandler();
        // 创建简单个事转换对象
        SimpleFormatter simpleFormatter = new SimpleFormatter();
        // 进行关联
        consoleHandler.setFormatter(simpleFormatter);
        logger.addHandler(consoleHandler);
        // 配置日志具体级别
        logger.setLevel(Level.ALL);
        consoleHandler.setLevel(Level.ALL);
        // 场景模拟 FileHandler  文件输出
        FileHandler fileHandler = new FileHandler("d:/logs/jul.log");
        // 进行关联
        fileHandler.setFormatter(simpleFormatter);
        logger.addHandler(fileHandler);

        logger.severe("server");
        logger.warning("warning");
        logger.info("info");
        logger.config("config");
        logger.fine("fine");
        logger.finer("finer");
        logger.finest("finest");
    }

    @Test
    void testLogParent() {
        //日志级别
        Logger logger1 = Logger.getLogger("com.zhuang");
        Logger logger2 = Logger.getLogger("com");
        Logger parent = logger1.getParent();
        System.out.println(parent == logger2);
        // 所有日志记录器对象的顶级父元素 class为java.util.logging.LogManager$RootLoggername为"
        System.out.println("logger2 parent:" + logger2.getParent() + "，name：" +
                logger2.getParent().getName());

        // 一、自定义日志级别
        // a.关闭系统默认配置
        logger2.setUseParentHandlers(false);
        // b.创建handler对象
        ConsoleHandler consoleHandler = new ConsoleHandler();
        // c.创建formatter对象
        SimpleFormatter simpleFormatter = new SimpleFormatter();
        // d.进行关联
        consoleHandler.setFormatter(simpleFormatter);
        logger2.addHandler(consoleHandler);
        // e.设置日志级别
        logger2.setLevel(Level.ALL);
        consoleHandler.setLevel(Level.ALL);
        // 测试日志记录器对象父子关系
        logger1.severe("severe");
        logger1.warning("warning");
        logger1.info("info");
        logger1.config("config");
        logger1.fine("fine");
        logger1.finer("finer");
        logger1.finest("finest");
    }

    @Test
    void testProperties() throws IOException {
        // 读取自定义配置文件
        InputStream in = SpringBootLogsApplicationTests.class.getClassLoader().getResourceAsStream("logging.properties");
        // 获取日志管理器对象
        LogManager logManager = LogManager.getLogManager();
        // 通过日志管理器加载配置文件
        logManager.readConfiguration(in);
        Logger logger = Logger.getLogger("com.zhuang.springbootlogs.SpringBootLogsApplicationTests");
        logger.severe("severe");
        logger.warning("warning");
        logger.info("info");
        logger.config("config");
        logger.fine("fine");
        logger.finer("finer");
        logger.finest("finest");

    }

    @Test
    void testQuickLog4j() {
        // 初始化配置信息
        BasicConfigurator.configure();
        org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger("com.zhuang.springbootlogs.SpringBootLogsApplicationTests");
        logger.info("hello log4j");

        //日志级别
        logger.fatal("fatal"); // 严重错误，一般会造成系统崩溃和终止运行
        logger.error("error"); // 错误信息，但不会影响系统运行
        logger.warn("warn"); // 警告信息，可能会发生问题
        logger.info("info"); // 程序运行信息，数据库的连接、网络、IO操作等
        logger.debug("debug"); // 调试信息，一般在开发阶段使用，记录程序的变量、参数等
        logger.trace("trace"); // 追踪信息，记录程序的所有流程信息
    }
}
