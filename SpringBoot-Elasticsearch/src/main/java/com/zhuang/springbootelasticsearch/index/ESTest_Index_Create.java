package com.zhuang.springbootelasticsearch.index;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;

import java.io.IOException;
/**
 * @author Zhuang
 */
public class ESTest_Index_Create {
    public static void main(String[] args) throws IOException {
        // 创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        // 创建索引
        CreateIndexRequest request = new CreateIndexRequest("student");
        CreateIndexResponse response = esClient.indices().create(request, RequestOptions.DEFAULT);

        // 响应状态
        boolean acknowledged = response.isAcknowledged();
        System.out.println("索引操作" + acknowledged);


        // 关闭ES客户端
        esClient.close();
    }
}
