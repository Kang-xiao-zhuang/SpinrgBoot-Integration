package com.zhuang.mapper;

import com.zhuang.domain.SysUser;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SysUserMapper {
    //根据用户名称查询所对应的用户信息
    @Select("select * from `sys_user` where `username` = #{username}")
    @Results({
            //主键字段映射，property代表Java对象属性，column代表数据库字段
            @Result(property = "id", column = "id", id = true),
            //普通字段映射，property代表Java对象属性，column代表数据库字段
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "status", column = "status"),
            //角色列表映射，根据用户id查询该用户所对应的角色列表sysRoles
            @Result(property = "sysRoles", column = "id",
                    javaType = List.class,
                    many = @Many(select = "com.zhuang.mapper.SysRoleMapper.findByUid")
            )
    })
    SysUser findByUsername(String username);
}