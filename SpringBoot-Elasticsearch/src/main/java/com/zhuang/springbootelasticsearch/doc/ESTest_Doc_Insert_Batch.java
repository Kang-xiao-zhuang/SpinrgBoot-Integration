package com.zhuang.springbootelasticsearch.doc;

import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

/**
 * @author Zhuang
 */
public class ESTest_Doc_Insert_Batch {
    public static void main(String[] args) throws IOException {
        // 创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );

        BulkRequest resquest = new BulkRequest();
        // 批量添加
        resquest.add(new IndexRequest().index("student").id("1002").source(XContentType.JSON, "name", "lisi","age",15,"sex","男"));
        resquest.add(new IndexRequest().index("student").id("1003").source(XContentType.JSON, "name", "wangwu","age",18,"sex","女"));
        resquest.add(new IndexRequest().index("student").id("1004").source(XContentType.JSON, "name", "zhaoliu","age",20,"sex","女"));
        resquest.add(new IndexRequest().index("student").id("1004").source(XContentType.JSON, "name", "tianqi","age",30,"sex","女"));

        BulkResponse responses = esClient.bulk(resquest, RequestOptions.DEFAULT);
        System.out.println("responses.getTook() = " + responses.getTook());
        System.out.println("responses.getItems() = " + responses.getItems());
        // 关闭ES客户端
        esClient.close();
    }
}
