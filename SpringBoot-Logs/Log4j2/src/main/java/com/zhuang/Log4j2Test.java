package com.zhuang;

import org.junit.Test;

import org.apache.logging.log4j.*;

public class Log4j2Test {

    public static final Logger LOGGER = LogManager.getLogger(Log4j2Test.class);


    @Test
    public void testQuick(){
        LOGGER.fatal("fatal");
        LOGGER.error("error");
        LOGGER.warn("warn");
        LOGGER.debug("debug");
        LOGGER.trace("trace");
    }
}
