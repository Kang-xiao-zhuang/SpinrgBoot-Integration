package com.zhuang.springbootelasticsearch.controller;

import com.zhuang.springbootelasticsearch.dao.ProductDao;
import com.zhuang.springbootelasticsearch.entity.Product;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "es产品测试")
@RestController
public class ProductController {

    @Autowired
    private ProductDao productDao;

/*    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;*/

    //根据id查询
    @GetMapping("/find")
    @ApiOperation("根据id查询")
    public Product findById() {
        Product product = productDao.findById(2L).get();
        System.out.println("product = " + product);
        return product;
    }

    //查询所有
    @GetMapping("findAll")
    @ApiOperation("查询所有")
    Iterable<Product> findAll() {
        Iterable<Product> products = productDao.findAll();
        for (Product product : products) {
            System.out.println(product);
        }
        return products;
    }

    @PostMapping("/update")
    @ApiOperation("更新数据")
    Product update() {
        Product product = new Product();
        product.setId(3L);
        product.setTitle("小辣椒手机");
        product.setCategory("手机");
        product.setPrice(8888.0);
        product.setImages("http://itkxz.cn/hw.jpg");
        Product save = productDao.save(product);
        System.out.println("save = " + save);
        return save;
    }

    @GetMapping("/findPage")
    @ApiOperation("分页查询")
    List<Product> findByPageable() {
        //分页查询
        // 设置排序（排序方式，倒序还是倒序，排序的id）
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        ArrayList<Product> productList = new ArrayList<>();
        int from = 0;
        int size = 2;
        // 设置查询分页
        PageRequest pageRequest = PageRequest.of(from, size, sort);
        Page<Product> productPage = productDao.findAll(pageRequest);
        // 分页查询
        for (Product product : productPage.getContent()) {
            System.out.println("product = " + product);
            productList.add(product);
        }
        return productList;
    }
}
