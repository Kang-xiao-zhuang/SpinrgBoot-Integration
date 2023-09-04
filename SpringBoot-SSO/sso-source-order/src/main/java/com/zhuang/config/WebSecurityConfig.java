package com.zhuang.config;

import com.zhuang.filter.JwtVerificationFilter;
import com.zhuang.prop.RsaKeyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private RsaKeyProperties prop;

    public void configure(HttpSecurity http) throws Exception {
        //禁用csrf保护机制
        http.csrf().disable();
        //禁用cors保护机制
        http.cors().disable();
        //禁用session会话
        http.sessionManagement().disable();
        //禁用form表单登录
        http.formLogin().disable();
        //增加自定义验证过滤器（资源服务需要配置）
        http.addFilter(new JwtVerificationFilter(super.authenticationManager(), prop));
    }
}