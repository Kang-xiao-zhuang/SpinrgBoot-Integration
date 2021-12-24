package com.zhuang.springbootelasticsearch.controller;

import com.zhuang.springbootelasticsearch.dao.ContentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Classname ContentController
 * @Description Content控制层
 * @Date 2021/12/24 20:21
 * @Author by dell
 */
@RestController
public class ContentController {
    @Autowired
    private ContentDao contentDao;


    @GetMapping("/parse/{keyword}")
    public Boolean parse(@PathVariable("keyword") String keyword) throws Exception {
        return contentDao.parseContent(keyword);
    }

    @GetMapping("/search/{keyword}/{pageNo}/{pageSize}")
    public List<Map<String, Object>> search(@PathVariable("keyword") String keyword, @PathVariable("pageNo") int pageNo, @PathVariable("pageSize") int pageSize) throws Exception {
        return contentDao.searchPage(keyword, pageNo, pageSize);
    }
}
