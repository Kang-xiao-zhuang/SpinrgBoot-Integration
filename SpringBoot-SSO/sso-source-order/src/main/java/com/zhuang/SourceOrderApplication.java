package com.zhuang;

import com.zhuang.prop.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class SourceOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(SourceOrderApplication.class, args);
    }
}