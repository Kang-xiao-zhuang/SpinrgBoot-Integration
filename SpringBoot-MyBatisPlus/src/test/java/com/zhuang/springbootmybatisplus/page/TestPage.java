package com.zhuang.springbootmybatisplus.page;

import com.zhuang.springbootmybatisplus.service.TUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestPage {

    @Autowired
    private TUserService userService;

    @Test
    void testPage(){
        System.out.println(userService.getPageInfo());
    }


}
