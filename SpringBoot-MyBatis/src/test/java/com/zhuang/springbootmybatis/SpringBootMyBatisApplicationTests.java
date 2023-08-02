package com.zhuang.springbootmybatis;

import com.zhuang.springbootmybatis.entity.User;
import com.zhuang.springbootmybatis.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootMyBatisApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
    }

    @Test
    void testFindOne() {
        User one = userService.findOne(1);
        System.out.println("one = " + one);
    }

    @Test
    void testFindAll() {
        userService.findAll().forEach(System.out::println);
    }

    @Test
    void testAddUser() {
        User user = new User();
        user.setUname("康小庄");
        user.setUage(28);
        user.setUgender("男");
        System.out.println(userService.addUser(user) == 1 ? "添加成功" : "添加失败");
    }

    @Test
    void testDeleteById() {
        System.out.println(userService.deleteById(6) == 1 ? "删除成功" : "删除失败");
    }

    @Test
    void testUpdateById() {
        User updateUser = userService.findOne(5);
        updateUser.setUage(23);
        System.out.println(userService.updateById(updateUser) == 1 ? "更新成功" : "更新失败");
    }
}
