package com.zhuang.springbootmybatisplus.service.impl;

import com.zhuang.springbootmybatisplus.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhuang.springbootmybatisplus.entity.User;
import com.zhuang.springbootmybatisplus.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author KangXiaoZhuang
 * @since 2023-08-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
