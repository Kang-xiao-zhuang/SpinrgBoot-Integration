package com.zhuang.mapper;

import com.zhuang.domain.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SysRoleMapper {
    //根据用户编号查询角色列表
    @Select("select * from `sys_role` where id in (" +
            "  select rid from `sys_user_role` where uid = #{uid}" +
            ")")
    List<SysRole> findByUid(Integer uid);
}