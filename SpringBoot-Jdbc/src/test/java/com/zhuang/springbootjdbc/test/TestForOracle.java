package com.zhuang.springbootjdbc.test;

import com.zhuang.springbootjdbc.dao.EduDao;
import com.zhuang.springbootjdbc.entity.Edu;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class TestForOracle {
    @Autowired
    private EduDao eduDao;

    @Test
    void testAdd() {
        Edu edu = new Edu();
        edu.setName("zhangsan-FAGB");
        edu.setLasteventname("TEST0123");
        System.out.println(eduDao.addOne(edu));
    }

    @Test
    void testDelete() {
        Edu edu = new Edu();
        edu.setName("zhangsan-FAGB");
        Integer count = eduDao.deleteById(edu.getName());
        System.out.println(count);
    }

    @Test
    void testUpdate() {
        Edu edu = new Edu();
        edu.setName("zhangsan-FAGB");
        edu.setLasteventcomment("CreateEdu");
        Integer count = eduDao.updateById(edu);
        System.out.println(count);
    }

    @Test
    void testQuery() {
        Edu edu = eduDao.getOneById("FAGB13002");
        System.out.println(edu);
    }

    @Test
    void testQueryAll() {
        List<Edu> eduList = eduDao.getAll();
        eduList.forEach(System.out::println);
    }
}
