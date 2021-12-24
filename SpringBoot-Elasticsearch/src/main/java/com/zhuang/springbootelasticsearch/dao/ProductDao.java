package com.zhuang.springbootelasticsearch.dao;

import com.zhuang.springbootelasticsearch.entity.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Zhuang
 */
@Repository
public interface ProductDao extends ElasticsearchRepository<Product,Long>{
}
