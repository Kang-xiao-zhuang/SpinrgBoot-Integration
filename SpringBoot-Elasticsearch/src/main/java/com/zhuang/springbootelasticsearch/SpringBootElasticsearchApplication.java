package com.zhuang.springbootelasticsearch;

//import com.zhuang.springbootelasticsearch.utils.ElasticsearchUtil;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@SpringBootApplication
@EnableSwagger2
public class SpringBootElasticsearchApplication {
	public  static final String INDEX_NAME = "jd_goods";
	public static void main(String[] args) {

		SpringApplication.run(SpringBootElasticsearchApplication.class, args);
	}

/*	@Resource
	private RestHighLevelClient client;*/

/*	@PostConstruct
	public void initIndex(){
		ElasticsearchUtil util = new ElasticsearchUtil(client);
		util.createIndex(INDEX_NAME);
	}*/
}
