package com.zhuang.springbootelasticsearch.dao;

import com.zhuang.springbootelasticsearch.entity.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @Classname ProductDao
 * @Description Dao层
 * @Date 2021/12/23 19:21
 * @Author by dell
 */
@Repository
public interface ProductDao extends ElasticsearchRepository<Product,Long>{
}
