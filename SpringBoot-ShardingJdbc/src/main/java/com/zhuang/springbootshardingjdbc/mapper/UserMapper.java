package com.zhuang.springbootshardingjdbc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhuang.springbootshardingjdbc.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {
}
