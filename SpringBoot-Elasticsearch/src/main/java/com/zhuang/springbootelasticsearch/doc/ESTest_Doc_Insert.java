package com.zhuang.springbootelasticsearch.doc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhuang.springbootelasticsearch.entity.Student;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

/**
 * @Classname ESTest_Doc_Insert
 * @Description 插入文档
 * @Date 2021/12/22 19:21
 * @Author by dell
 */
public class ESTest_Doc_Insert {
    public static void main(String[] args) throws IOException {
        // 创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("101.43.21.132", 9200, "http"))
        );

        IndexRequest resquest = new IndexRequest();
        resquest.index("student").id("1001");
        Student student = new Student();
        student.setName("张三");
        student.setSex("男");
        student.setAge(18);

        // 转为JSON
        ObjectMapper mapper = new ObjectMapper();
        String studentJson = mapper.writeValueAsString(student);
        resquest.source(studentJson, XContentType.JSON);

        IndexResponse response = esClient.index(resquest, RequestOptions.DEFAULT);
        System.out.println("response.getResult() = " + response.getResult());
        // 关闭ES客户端
        esClient.close();
    }
}
