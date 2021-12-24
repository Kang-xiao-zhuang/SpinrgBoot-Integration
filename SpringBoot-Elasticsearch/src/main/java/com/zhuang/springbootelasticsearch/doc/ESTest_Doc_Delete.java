package com.zhuang.springbootelasticsearch.doc;

import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * @Classname ESTest_Doc_Delete
 * @Description 删除文档
 * @Date 2021/12/23 19:21
 * @Author by dell
 */
public class ESTest_Doc_Delete {
    public static void main(String[] args) throws IOException {
        // 创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("101.43.21.132", 9200, "http"))
        );

        DeleteRequest request = new DeleteRequest();
        request.index("student").id("1001");

        DeleteResponse response = esClient.delete(request, RequestOptions.DEFAULT);
        System.out.println("response.getResult() = " + response.toString());
        // 关闭ES客户端
        esClient.close();
    }
}
