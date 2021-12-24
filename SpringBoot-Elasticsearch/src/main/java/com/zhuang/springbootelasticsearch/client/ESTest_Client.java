package com.zhuang.springbootelasticsearch.client;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * @Classname ESTest_Client
 * @Description 创建本地的es客户端
 * @Date 2021/12/23 20:21
 * @Author by dell
 */
public class ESTest_Client {
    public static void main(String[] args) throws IOException {
        // 创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );
        // 关闭ES客户端
        esClient.close();
    }
}
