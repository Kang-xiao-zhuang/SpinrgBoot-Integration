package com.zhuang.springbootmybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zhuang.springbootmybatisplus.mapper")
public class SpringBootMyBatisPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMyBatisPlusApplication.class, args);
    }

}
