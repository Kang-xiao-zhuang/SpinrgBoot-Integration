package com.zhuang.springbootmybatisplus.serviceapi;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhuang.springbootmybatisplus.entity.TUser;
import com.zhuang.springbootmybatisplus.service.TUserService;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.naming.Name;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class MybatisServiceTest {

    @Autowired
    private TUserService userService;


    @Test
    void testSaveOrUpdate() {
        TUser user = new TUser();
        user.setUid(9);
        user.setUname("mybatis-plus测试");
        user.setUage(28);
        user.setUgender("男");
        System.out.println(userService.saveOrUpdate(user) ? "添加成功" : "添加失败");
    }

    @Test
    void testUpdateWrapper() {
        UpdateWrapper<TUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("uid", 10);
        TUser user = userService.getOne(updateWrapper);
        user.setUname("修改");
        System.out.println(userService.saveOrUpdate(user) ? "修改成功" : "修改失败");
    }

    @Test
    void testSaveBatch() {
        ArrayList<TUser> userList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TUser user = new TUser();
            user.setUname("user-" + i);
            user.setUage(i);
            user.setUgender("男");
            userList.add(user);
        }
        userService.saveBatch(userList);
    }

    @Test
    void testRemove() {
        LambdaQueryWrapper<TUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TUser::getUgender, "男").eq(TUser::getUage, 9);
        System.out.println(userService.remove(queryWrapper) ? "删除成功" : "删除失败");
    }

    @Test
    void testPage(){
        // todo 未debug完全
        Page<TUser> page = userService.page(new Page<>(1,2));
        System.out.println("总页数： " + page.getPages());
        System.out.println("总数量： " +page.getRecords());
    }
}
