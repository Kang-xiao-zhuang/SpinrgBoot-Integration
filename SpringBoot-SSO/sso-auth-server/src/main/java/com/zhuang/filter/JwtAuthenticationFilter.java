package com.zhuang.filter;

import com.zhuang.domain.SysUser;
import com.zhuang.prop.RsaKeyProperties;
import com.zhuang.utils.JwtUtils;
import com.zhuang.utils.RequestUtils;
import com.zhuang.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 认证过滤器
 */
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final RsaKeyProperties prop;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, RsaKeyProperties prop) {
        this.authenticationManager = authenticationManager;
        this.prop = prop;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        SysUser sysUser = RequestUtils.read(request, SysUser.class);
        assert sysUser != null;
        String username = sysUser.getUsername();
        username = username != null ? username : "";
        String password = sysUser.getPassword();
        password = password != null ? password : "";
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authRequest);
    }

    /**
     * 认证成功所执行的方法
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SysUser sysUser = new SysUser();
        sysUser.setUsername(authResult.getName());
        sysUser.setSysRoles(new ArrayList(authResult.getAuthorities()));
        String token = JwtUtils.generateTokenExpireInMinutes(sysUser, prop.getPrivateKey(), 24 * 60);
        response.addHeader("Authorization", "Bearer " + token);
        ResponseUtils.write(response, HttpServletResponse.SC_OK, "用户认证通过！");
    }

    /**
     * 认证失败所执行的方法
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        //清理上下文
        SecurityContextHolder.clearContext();
        log.error(failed.getMessage());
        //判断异常类
        if (failed instanceof InternalAuthenticationServiceException) {
            ResponseUtils.write(response, HttpServletResponse.SC_FORBIDDEN, "认证服务不正常！");
        } else if (failed instanceof UsernameNotFoundException) {
            ResponseUtils.write(response, HttpServletResponse.SC_FORBIDDEN, "用户账户不存在！");
        } else if (failed instanceof BadCredentialsException) {
            ResponseUtils.write(response, HttpServletResponse.SC_FORBIDDEN, "用户密码是错的！");
        } else if (failed instanceof AccountExpiredException) {
            ResponseUtils.write(response, HttpServletResponse.SC_FORBIDDEN, "用户账户已过期！");
        } else if (failed instanceof LockedException) {
            ResponseUtils.write(response, HttpServletResponse.SC_FORBIDDEN, "用户账户已被锁！");
        } else if (failed instanceof CredentialsExpiredException) {
            ResponseUtils.write(response, HttpServletResponse.SC_FORBIDDEN, "用户密码已失效！");
        } else if (failed instanceof DisabledException) {
            ResponseUtils.write(response, HttpServletResponse.SC_FORBIDDEN, "用户账户已被锁！");
        }
    }
}