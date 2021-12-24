
package com.zhuang.springbootelasticsearch.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchJdConfig {
    @Bean
    public RestHighLevelClient restHighLevelClient(@Value("${elasticsearch.node.path:http://127.0.0.1:9200}") String path) {
        String[] split = path.split(":");
        String scheme = split[0];
        String host = split[1].split("//")[1];
        Integer port = Integer.valueOf(split[2]);
        return new RestHighLevelClient(RestClient.builder(new HttpHost(host, port, scheme)));
    }
}

