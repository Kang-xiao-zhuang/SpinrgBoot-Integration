package com.zhuang.springbootelasticsearch.index;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
/**
 * @author Zhuang
 */
public class ESTest_Index_Search {
    public static void main(String[] args) throws Exception {
        // 创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        // 创建索引
        GetIndexRequest request = new GetIndexRequest("student");
        GetIndexResponse response = esClient.indices().get(request, RequestOptions.DEFAULT);

        System.out.println("response.getAliases() = " + response.getAliases());
        System.out.println("response.getMappings() = " + response.getMappings());
        System.out.println("response.getSettings() = " + response.getSettings());

        // 关闭ES客户端
        esClient.close();
    }
}
