package com.zhuang.es.springdataelasticsearch.dao;

import com.zhuang.es.springdataelasticsearch.entity.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

//@Repository
@Service
public interface ProductDao extends ElasticsearchRepository<Product,Long> {

}
