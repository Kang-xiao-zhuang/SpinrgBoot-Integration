package com.zhuang.springbootelasticsearch;

import com.zhuang.springbootelasticsearch.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

@SpringBootTest
class SpringBootElasticsearchApplicationTests {

    //注入 ElasticsearchRestTemplate
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Test
    void contextLoads() {
    }

    //创建索引并增加映射配置
    @Test
    public void createIndex() {
        //创建索引，系统初始化会自动创建索引
        System.out.println("创建索引");
    }

    @Test
    void deleteIndex() {
        // 删除索引
        boolean flag = elasticsearchRestTemplate.indexOps(Product.class).delete();
        System.out.println("flag = " + flag);
    }
}
