package com.zhuang.springbootelasticsearch.doc;

import org.apache.http.HttpHost;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

/**
 * @Classname ESTest_Doc_Update
 * @Description 更新文档
 * @Date 2021/12/21 19:21
 * @Author by dell
 */
public class ESTest_Doc_Update {
    public static void main(String[] args) throws IOException {
        // 创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("101.43.21.132", 9200, "http"))
        );


        //修改数据
        UpdateRequest request = new UpdateRequest();
        request.index("student").id("1001");
        request.doc(XContentType.JSON, "sex", "女");

        UpdateResponse response = esClient.update(request, RequestOptions.DEFAULT);

        System.out.println("response.getResult() = " + response.getResult());
        // 关闭ES客户端
        esClient.close();
    }
}
