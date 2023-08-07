package com.zhuang.springbootmybatisplus.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhuang.springbootmybatisplus.entity.TUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author KangXiaoZhuang
 * @since 2023-08-03
 */
@Mapper
public interface TUserMapper extends BaseMapper<TUser> {
    /**
     * 根据年龄查询用户列表，分页显示
     * @param page 分页对象,xml中可以从里面进行取值,传递参数 Page 即自动分页,必须放在第一位
     * @param uage 年龄
     * @return IPage
     */
    IPage<TUser> selectPageVo(@Param("page") Page<TUser> page, @Param("uage") Integer uage);

}
