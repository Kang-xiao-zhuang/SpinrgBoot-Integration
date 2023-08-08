package com.zhuang.springbootelasticsearch;

import com.zhuang.springbootelasticsearch.dao.ProductDao;
import com.zhuang.springbootelasticsearch.entity.Product;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import java.util.ArrayList;

@SpringBootTest
class SpringBootElasticsearchApplicationTests {

    //注入 ElasticsearchRestTemplate
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private ProductDao productDao;

    @Test
    void contextLoads() {
    }

    //创建索引并增加映射配置
    @Test
    public void createIndex() {
        //创建索引，系统初始化会自动创建索引
        System.out.println("创建索引");
    }

    @Test
    void deleteIndex() {
        // 删除索引
        boolean flag = elasticsearchRestTemplate.indexOps(Product.class).delete();
        System.out.println("flag = " + flag);
    }

    @Test
    void save() {
        Product product = new Product();
        product.setId(2L);
        product.setTitle("华为手机");
        product.setCategory("手机");
        product.setPrice(2999.0);
        product.setImages("https://itkxz.cn/hw.jpg");
        productDao.save(product);
    }

    @Test
    void update() {
        Product product = new Product();
        product.setId(2L);
        product.setTitle("小米手机");
        product.setCategory("手机");
        product.setPrice(2999.0);
        product.setImages("https://itkxz.cn/hw.jpg");
        Product save = productDao.save(product);
        System.out.println("save = " + save);
    }

    //根据id查询
    @Test
    void findById() {
        Product product = productDao.findById(2L).get();
        System.out.println("product = " + product);
    }

    //查询所有
    @Test
    void findAll() {
        Iterable<Product> products = productDao.findAll();
        for (Product product : products) {
            System.out.println(product);
        }
    }

    //删除
    @Test
    void delete() {
        Product product = new Product();
        product.setId(2L);
        productDao.delete(product);
    }

    @Test
    void saveAll() {
        ArrayList<Product> productList = new ArrayList<>();
        for (int i = 3; i < 6; i++) {
            Product product = new Product();
            product.setId((long) i);
            product.setTitle("[" + i + "]小米手机");
            product.setCategory("手机");
            product.setPrice(1999.0 + i);
            product.setImages("https://www.atguigu/xm.jpg");
            productList.add(product);
        }
        productDao.saveAll(productList);
    }

    @Test
    void findByPageable() {
        //分页查询
        // 设置排序（排序方式，倒序还是倒序，排序的id）
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        int from = 0;
        int size = 2;
        // 设置查询分页
        PageRequest pageRequest = PageRequest.of(from, size, sort);
        Page<Product> productPage = productDao.findAll(pageRequest);
        // 分页查询
        for (Product product : productPage.getContent()) {
            System.out.println("product = " + product);
        }
    }

    @Test
    void termQuery() {
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("id", 3);
        Iterable<Product> products = productDao.search(termQueryBuilder);
        for (Product product : products) {
            System.out.println("product = " + product);
        }
    }

    /**
     * term 查询加分页
     */
    @Test
    public void termQueryByPage() {
        int currentPage = 0;
        int pageSize = 5;
        //设置查询分页
        PageRequest pageRequest = PageRequest.of(currentPage, pageSize);
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", " 小米");
        Iterable<Product> products = productDao.search(termQueryBuilder, pageRequest);
        for (Product product : products) {
            System.out.println(product);
        }
    }
}
