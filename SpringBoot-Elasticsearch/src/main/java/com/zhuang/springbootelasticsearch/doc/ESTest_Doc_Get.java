package com.zhuang.springbootelasticsearch.doc;

import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * @Classname ESTest_Doc_Get
 * @Description 查询文档
 * @Date 2021/12/23 19:21
 * @Author by dell
 */
public class ESTest_Doc_Get {
    public static void main(String[] args) throws IOException {
        // 创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("101.43.21.132", 9200, "http"))
        );


        //修改数据
        GetRequest request = new GetRequest();
        request.index("student").id("1001");

        GetResponse response = esClient.get(request, RequestOptions.DEFAULT);

        System.out.println("response.getResult() = " + response.getSourceAsString());
        // 关闭ES客户端
        esClient.close();
    }
}
