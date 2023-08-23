package com.zhuang.springbootshardingjdbc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zhuang.springbootshardingjdbc.mapper")
public class SpringBootShardingJdbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootShardingJdbcApplication.class, args);
    }

}
