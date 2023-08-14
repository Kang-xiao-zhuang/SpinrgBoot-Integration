package com.zhuang.springbootmybatisplus.service.impl;

import com.zhuang.springbootmybatisplus.entity.TUser;
import com.zhuang.springbootmybatisplus.mapper.TUserMapper;
import com.zhuang.springbootmybatisplus.page.PageData;
import com.zhuang.springbootmybatisplus.service.TUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author KangXiaoZhuang
 * @since 2023-08-03
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements TUserService {

    @Autowired
    private TUserMapper userMapper;

    @Override
    public PageData<TUser> getPageInfo() {
        List<TUser> users = userMapper.selectList(null);
        return PageData.build(users,users.size());
    }
}
