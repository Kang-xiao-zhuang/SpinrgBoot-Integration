package com.zhuang.springbootmybatisplus.service;

import com.zhuang.springbootmybatisplus.entity.TUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhuang.springbootmybatisplus.page.PageData;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author KangXiaoZhuang
 * @since 2023-08-03
 */
public interface TUserService extends IService<TUser> {

    PageData<TUser> getPageInfo();


}
