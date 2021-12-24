package com.zhuang.springbootelasticsearch.doc;

import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * @Classname ESTest_Doc_Delete
 * @Description 批量删除文档
 * @Date 2021/12/23 19:21
 * @Author by dell
 */
public class ESTest_Doc_Delete_Batch {
    public static void main(String[] args) throws IOException {
        // 创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("101.43.21.132", 9200, "http"))
        );

        BulkRequest resquest = new BulkRequest();
        // 批量添加
        resquest.add(new DeleteRequest().index("student").id("1002"));
        resquest.add(new DeleteRequest().index("student").id("1003"));
        resquest.add(new DeleteRequest().index("student").id("1004"));

        BulkResponse responses = esClient.bulk(resquest, RequestOptions.DEFAULT);
        System.out.println("responses.getTook() = " + responses.getTook());
        System.out.println("responses.getItems() = " + responses.getItems());
        // 关闭ES客户端
        esClient.close();
    }
}
