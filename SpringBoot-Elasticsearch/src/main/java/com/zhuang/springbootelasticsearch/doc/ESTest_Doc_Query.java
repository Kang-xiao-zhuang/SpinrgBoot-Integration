package com.zhuang.springbootelasticsearch.doc;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;


import java.io.IOException;

/**
 * @Classname ESTest_Doc_Query
 * @Description 查询花样
 * @Date 2021/12/21 19:21
 * @Author by dell
 */
public class ESTest_Doc_Query {
    public static void main(String[] args) throws IOException {
        // 创建ES客户端
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("101.43.21.132", 9200, "http"))
        );


        //全部查询
/*        SearchRequest request = new SearchRequest();
        request.indices("student");
        request.source(new SearchSourceBuilder().query(QueryBuilders.matchAllQuery()));

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        System.out.println("response.getResult() = " + response.getHits().getTotalHits());
        System.out.println("response.getResult() = " + response.getTook());

        for (SearchHit hit : response.getHits()) {
            System.out.println("hit.getSourceAsString() = " + hit.getSourceAsString());
        }*/

/*        // 条件查询
        SearchRequest request = new SearchRequest();
        request.indices("student");
        request.source(new SearchSourceBuilder().query(QueryBuilders.termQuery("age", 30)));

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        System.out.println("response.getResult() = " + response.getHits().getTotalHits());
        System.out.println("response.getResult() = " + response.getTook());

        for (SearchHit hit : response.getHits()) {
            System.out.println("hit.getSourceAsString() = " + hit.getSourceAsString());
        }*/

        /*// 分页查询
        SearchRequest request = new SearchRequest();
        request.indices("student");
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        // （当前页码-1）*每页显示数据条数
        builder.from(0);
        builder.size(2);
        request.source(builder);

        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        System.out.println("response.getResult() = " + response.getHits().getTotalHits());
        System.out.println("response.getResult() = " + response.getTook());

        for (SearchHit hit : response.getHits()) {
            System.out.println("hit.getSourceAsString() = " + hit.getSourceAsString());
        }*/

/*        // 查询排序
        SearchRequest request = new SearchRequest();
        request.indices("student");
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        builder.sort("age", SortOrder.DESC);
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        System.out.println("response.getResult() = " + response.getHits().getTotalHits());
        System.out.println("response.getResult() = " + response.getTook());

        for (SearchHit hit : response.getHits()) {
            System.out.println("hit.getSourceAsString() = " + hit.getSourceAsString());
        }*/

       /* // 过滤字段
        SearchRequest request = new SearchRequest();
        request.indices("student");
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        String[] excludes = {"age"};
        String[] includes = {};
        builder.fetchSource(includes, excludes);
        request.source(builder);
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        System.out.println("response.getResult() = " + response.getHits().getTotalHits());
        System.out.println("response.getResult() = " + response.getTook());

        for (SearchHit hit : response.getHits()) {
            System.out.println("hit.getSourceAsString() = " + hit.getSourceAsString());
        }*/

/*        // 组合查询
        SearchRequest request = new SearchRequest();
        request.indices("student");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();


//        boolQueryBuilder.must(QueryBuilders.matchQuery("age", 30));
//        boolQueryBuilder.mustNot(QueryBuilders.matchQuery("age", 30));
        boolQueryBuilder.should(QueryBuilders.matchQuery("age",30));
        boolQueryBuilder.should(QueryBuilders.matchQuery("age",18));

        builder.query(boolQueryBuilder);

        request.source(builder);
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        System.out.println("response.getResult() = " + response.getHits().getTotalHits());
        System.out.println("response.getResult() = " + response.getTook());

        for (SearchHit hit : response.getHits()) {
            System.out.println("hit.getSourceAsString() = " + hit.getSourceAsString());
        }*/

/*        // 范围查询
        SearchRequest request = new SearchRequest();
        request.indices("student");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("age");
        rangeQuery.gte(16);
        builder.query(rangeQuery);
        request.source(builder);
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        System.out.println("response.getResult() = " + response.getHits().getTotalHits());
        System.out.println("response.getResult() = " + response.getTook());

        for (SearchHit hit : response.getHits()) {
            System.out.println("hit.getSourceAsString() = " + hit.getSourceAsString());
        }*/


        /*// 模糊查询
        SearchRequest request = new SearchRequest();
        request.indices("student");
        SearchSourceBuilder builder = new SearchSourceBuilder();

        builder.query(QueryBuilders.fuzzyQuery("name", "wangewu").fuzziness(Fuzziness.ONE));
        request.source(builder);
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        System.out.println("response.getResult() = " + response.getHits().getTotalHits());
        System.out.println("response.getResult() = " + response.getTook());

        for (SearchHit hit : response.getHits()) {
            System.out.println("hit.getSourceAsString() = " + hit.getSourceAsString());
        }*/

        /*// 高亮查询
        SearchRequest request = new SearchRequest();
        request.indices("student");
        SearchSourceBuilder builder = new SearchSourceBuilder();

        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "lisi");
        builder.query(termQueryBuilder);
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font colr='red'>");
        highlightBuilder.postTags("</font>");
        highlightBuilder.field("name");
        builder.highlighter(highlightBuilder);
        request.source(builder);
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        System.out.println("response.getResult() = " + response.getHits().getTotalHits());
        System.out.println("response.getResult() = " + response.getTook());

        for (SearchHit hit : response.getHits()) {
            System.out.println("hit.getSourceAsString() = " + hit.getSourceAsString());
        }*/

        /*// 聚合查询
        SearchRequest request = new SearchRequest();
        request.indices("student");
        SearchSourceBuilder builder = new SearchSourceBuilder();

        AggregationBuilder aggregationBuilder = AggregationBuilders.max("maxAge").field("age");

        builder.aggregation(aggregationBuilder);
        request.source(builder);
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        System.out.println("response.getResult() = " + response.getHits().getTotalHits());
        System.out.println("response.getResult() = " + response.getTook());

        for (SearchHit hit : response.getHits()) {
            System.out.println("hit.getSourceAsString() = " + hit.getSourceAsString());
        }*/

        // 分组查询
        SearchRequest request = new SearchRequest();
        request.indices("student");
        SearchSourceBuilder builder = new SearchSourceBuilder();

        AggregationBuilder aggregationBuilder = AggregationBuilders.terms("ageGroup").field("age");

        builder.aggregation(aggregationBuilder);
        request.source(builder);
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);

        System.out.println("response.getResult() = " + response.getHits().getTotalHits());
        System.out.println("response.getResult() = " + response.getTook());

        for (SearchHit hit : response.getHits()) {
            System.out.println("hit.getSourceAsString() = " + hit.getSourceAsString());
        }
        // 关闭ES客户端
        esClient.close();
    }
}
