package com.zhuang.springbootelasticsearch.index;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
/**
 * @author Zhuang
 */
public class ESTest_Index_Delete {
    public static void main(String[] args) throws Exception {
        // 创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        // 创建索引
        DeleteIndexRequest request = new DeleteIndexRequest("student");
        AcknowledgedResponse response = esClient.indices().delete(request, RequestOptions.DEFAULT);

        System.out.println("response.isAcknowledged() = " + response.isAcknowledged());
        // 关闭ES客户端
        esClient.close();
    }
}
