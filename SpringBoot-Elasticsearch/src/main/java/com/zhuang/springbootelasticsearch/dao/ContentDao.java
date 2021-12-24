package com.zhuang.springbootelasticsearch.dao;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zhuang.springbootelasticsearch.SpringBootElasticsearchApplication;
import com.zhuang.springbootelasticsearch.entity.Content;
import com.zhuang.springbootelasticsearch.utils.ElasticsearchUtil;
import com.zhuang.springbootelasticsearch.utils.HtmlParseUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentDao {
    @NonNull
    private RestHighLevelClient client;

    public Boolean parseContent(String keyword) throws IOException {
        ElasticsearchUtil esUtil = new ElasticsearchUtil(client);
        //  List<Content> contents = HtmlParseUtil.parseJd(keyword);
//        List<JSONObject> collect = contents.stream().map(JSONUtil::parseObj).collect(Collectors.toList());
        //  List<JSONObject> collect = contents.stream().map(JSONUtil::parseObj).collect(Collectors.toList());
        //   return esUtil.bulkDocument(SpringBootElasticsearchApplication.INDEX_NAME,collect);
        return false;
    }
}
