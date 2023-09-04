package com.zhuang.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class SysRole implements GrantedAuthority {
    private Integer id;
    private String name;
    private String desc;

    @JsonIgnore
    @Override
    public String getAuthority() {
        return name;
    }
}