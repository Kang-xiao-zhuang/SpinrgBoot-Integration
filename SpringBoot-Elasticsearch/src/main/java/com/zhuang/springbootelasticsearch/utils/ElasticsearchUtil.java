package com.zhuang.springbootelasticsearch.utils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.client.RestHighLevelClient;
import cn.hutool.json.JSONObject;
import lombok.Data;
import lombok.NonNull;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Classname ElasticsearchUtil
 * @Description elasticsearch操作工具类
 * @Date 2021/12/21 19:21
 * @Author by dell
 */
@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElasticsearchUtil {
    @NonNull
    private RestHighLevelClient restHighLevelClient;

    /**
     * @param name 索引名称
     * @return boolean
     */
    public boolean createIndex(String name) {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(name);
        log.info("判断索引是否存在: {}", name);
        try {
            //先判断索引是否存在，然后在执行是否 添加指令
            if (!indexExits(name)) {
                CreateIndexResponse response = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
                return response.isAcknowledged();
            }
        } catch (IOException e) {
            log.warn("添加索引失败 {}", e.getMessage());
            e.printStackTrace();
        }
        return false;

    }

    /**
     * 判断索引库是否存在
     *
     * @param name 索引名称
     * @return boolean
     */
    public boolean indexExits(String name) {
        GetIndexRequest getIndexRequest = new GetIndexRequest(name);
        boolean exists = false;
        log.info("判断索引是否存在: {}", name);
        try {
            exists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.warn("判断索引是否存在异常  {}", e.getMessage());
            e.printStackTrace();
        }
        return exists;
    }

    /**
     * @param name 索引名称
     * @return boolean
     */
    public boolean deleteIndex(String name) {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(name);
        boolean exists = false;
        try {
            AcknowledgedResponse delete = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
            if (delete.isAcknowledged()) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exists;
    }

    /**
     * 测试添加日志记录文档
     *
     * @param indexName  indexName
     * @param jsonObject jsonObject
     * @return boolean
     */
    public boolean createDocument(String indexName, JSONObject jsonObject) {
        //组装数据
        //创建请求
        IndexRequest request = new IndexRequest(indexName);
        //设置规则
        request.timeout(TimeValue.timeValueSeconds(1));
        //将数据放入请求对象里面
        request.source(jsonObject);
        try {
            //发送请求  获取响应
            IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            RestStatus status = response.status();
            if (status.equals(RestStatus.OK)) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 判断文档是否存在
     *
     * @param indexName 索引名称
     * @param id        文档id
     * @return boolean
     */
    public boolean documentIsExists(String indexName, String id) {
        GetRequest request = new GetRequest(indexName, id);
        request.fetchSourceContext(new FetchSourceContext(false));
        request.storedFields("_none_");
        boolean exists = false;
        try {
            exists = restHighLevelClient.exists(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.warn("判断文档是否存在异常： {}", e.getMessage());
            e.printStackTrace();
        }
        return exists;
    }


    /**
     * 根据id获取文档
     *
     * @param indexName 索引名称
     * @param id        文档id
     * @return Map
     */
    public Map<String, Object> getDocument(String indexName, String id) {
        GetRequest request = new GetRequest(indexName, id);
        try {
            GetResponse documentFields = restHighLevelClient.get(request, RequestOptions.DEFAULT);
            return documentFields.getSource();
        } catch (IOException e) {
            log.warn("获取文档失败: {}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据文档id修改文档信息
     *
     * @param indexName  索引名称
     * @param id         文档id
     * @param jsonObject jsonObject
     * @return boolean
     */
    public boolean updateDocument(String indexName, String id, JSONObject jsonObject) {
        UpdateRequest request = new UpdateRequest(indexName, id);
        try {
            request.timeout(TimeValue.timeValueSeconds(1));
            request.doc(jsonObject, XContentType.JSON);
            UpdateResponse update = restHighLevelClient.update(request, RequestOptions.DEFAULT);
            if (update.status().equals(RestStatus.OK)) {
                return true;
            }
        } catch (IOException e) {
            log.warn("修改文档失败: {}", e.getMessage());
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 删除文档
     *
     * @param indexName 索引名称
     * @param id        文档id
     * @return boolean
     */
    public boolean deleteDocument(String indexName, String id) {
        DeleteRequest request = new DeleteRequest(indexName, id);
        try {
            DeleteResponse delete = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
            if (delete.status().equals(RestStatus.OK)) {
                return true;
            }
        } catch (IOException e) {
            log.warn("修改文档失败: {}", e.getMessage());
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 批量插入文档数据
     *
     * @param indexName 索引
     * @param list      批量文档
     * @return boolean
     */
    public boolean bulkDocument(String indexName, List<JSONObject> list) {
        BulkRequest bulkRequest = new BulkRequest(indexName);
        bulkRequest.timeout(TimeValue.timeValueSeconds(2));
        try {
            list.forEach(item -> bulkRequest.add(new IndexRequest().source(item, XContentType.JSON)));
            BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            if (bulk.status().equals(RestStatus.OK)) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
