package com.zhuang.springbootmybatis.mapper;

import com.zhuang.springbootmybatis.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
public interface UserMapper {

    /**
     * 查找一个用户
     *
     * @param id id
     * @return User实体
     */
    User findOne(@PathVariable Integer id);

    /**
     * 查找所有用户
     *
     * @return 用户集合
     */
    List<User> findAll();

    /**
     * 添加用户
     *
     * @param user User
     * @return 影响个数
     */
    Integer addUser(@PathVariable User user);

    /**
     * 根据id删除用户
     *
     * @param id 用户名id
     * @return 影响个数
     */
    Integer deleteById(@PathVariable Integer id);

    /**
     * 根据id更新用户
     *
     * @param user User
     * @return 影响个数
     */
    Integer updateById(@PathVariable User user);
}
