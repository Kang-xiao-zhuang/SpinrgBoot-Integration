package com.zhuang.springbootmybatisplus.activerecord;

import com.zhuang.springbootmybatisplus.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * ActiveRecord 模式
 * 实体类只需继承 Model 类即可进行强大的 CRUD 操作
 * 需要项目中已注入对应实体的BaseMapper
 */
@SpringBootTest
public class ActiveRecordTest {


    @Test
    void testInsert(){
        User user = new User();
        user.setName("测试插入");
        user.setAge(18);
        user.setGender("男");
        System.out.println(user.insert()? "插入成功":"插入失败");
    }

}
