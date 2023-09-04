package com.zhuang.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
public class SysUser implements UserDetails {
    private Integer id;
    private String username;
    private String password;
    private Integer status;
    private List<SysRole> sysRoles;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return sysRoles;
    }

    /**
     * 是否账号已过期
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return status != 1;
    }

    /**
     * 是否账号已被锁
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return status != 2;
    }

    /**
     * 是否凭证已过期
     */
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return status != 3;
    }

    /**
     * 是否账号已禁用
     */
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return status != 4;
    }
}