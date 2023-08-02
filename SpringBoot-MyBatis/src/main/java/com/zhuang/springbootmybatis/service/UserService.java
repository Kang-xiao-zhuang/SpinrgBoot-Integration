package com.zhuang.springbootmybatis.service;

import com.zhuang.springbootmybatis.entity.User;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface UserService {

    User findOne(@PathVariable Integer id);

    List<User> findAll();

    Integer addUser(@PathVariable User user);

    Integer deleteById(@PathVariable Integer id);

    Integer updateById(@PathVariable User user);
}
