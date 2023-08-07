package com.zhuang.springbootmybatisplus.mapperapi;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhuang.springbootmybatisplus.entity.TUser;
import com.zhuang.springbootmybatisplus.mapper.TUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MybatisMapperTest {

    @Autowired
    private TUserMapper mapper;

    @Test
    void testInsert() {
        TUser user = new TUser();
        user.setUage(18);
        user.setUname("zkkk");
        user.setUgender("男");
        System.out.println(mapper.insert(user) == 1 ? "新增成功" : "新增失败");
    }

    @Test
    void testDelete() {
        LambdaQueryWrapper<TUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(TUser::getUname, "zkkk");
        System.out.println(mapper.delete(lambdaQueryWrapper) == 1 ? "删除成功" : "删除失败");
    }

    @Test
    void testUpdate() {
        UpdateWrapper<TUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("uname", "修改");
        TUser user = mapper.selectOne(updateWrapper);
        user.setUname("mydelete");
        System.out.println(mapper.update(user, updateWrapper) == 1 ? "更新成功" : "更新失败");
    }

    @Test
    void testSelectList(){
        QueryWrapper<TUser> userQueryWrapper = new QueryWrapper<>();
        mapper.selectList(userQueryWrapper).forEach(System.out::println);
        mapper.selectList(userQueryWrapper).forEach(System.out::println);
    }

    @Test
    public void testPage(){
        //设置分页参数
        Page<TUser> page = new Page<>(1, 5);
        mapper.selectPage(page, null);
        //获取分页数据
        List<TUser> list = page.getRecords();
        list.forEach(System.out::println);
        System.out.println("当前页："+page.getCurrent());
        System.out.println("每页显示的条数："+page.getSize());
        System.out.println("总记录数："+page.getTotal());
        System.out.println("总页数："+page.getPages());
        System.out.println("是否有上一页："+page.hasPrevious());
        System.out.println("是否有下一页："+page.hasNext());
    }
}
