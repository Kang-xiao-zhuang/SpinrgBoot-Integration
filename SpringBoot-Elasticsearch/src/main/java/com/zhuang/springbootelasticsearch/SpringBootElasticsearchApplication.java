package com.zhuang.springbootelasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootElasticsearchApplication {

	public static void main(String[] args) {
//		System.setProperty("es.set.netty.runtime.available.processors", "false");
		SpringApplication.run(SpringBootElasticsearchApplication.class, args);
	}
}
