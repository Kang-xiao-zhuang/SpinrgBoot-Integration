package com.zhuang.springbootmybatis.service.impl;

import com.zhuang.springbootmybatis.entity.User;
import com.zhuang.springbootmybatis.mapper.UserMapper;
import com.zhuang.springbootmybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findOne(Integer id) {
        return userMapper.findOne(id);
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public Integer addUser(User user) {
        return userMapper.addUser(user);
    }

    @Override
    public Integer deleteById(Integer id) {
        return userMapper.deleteById(id);
    }

    @Override
    public Integer updateById( User user) {
        return userMapper.updateById(user);
    }
}
