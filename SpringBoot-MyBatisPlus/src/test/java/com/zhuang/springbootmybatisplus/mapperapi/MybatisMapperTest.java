package com.zhuang.springbootmybatisplus.mapperapi;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zhuang.springbootmybatisplus.entity.TUser;
import com.zhuang.springbootmybatisplus.mapper.TUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}
