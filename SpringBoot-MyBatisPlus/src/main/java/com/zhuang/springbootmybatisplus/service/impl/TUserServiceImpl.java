package com.zhuang.springbootmybatisplus.service.impl;

import com.zhuang.springbootmybatisplus.entity.TUser;
import com.zhuang.springbootmybatisplus.mapper.TUserMapper;
import com.zhuang.springbootmybatisplus.service.TUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
