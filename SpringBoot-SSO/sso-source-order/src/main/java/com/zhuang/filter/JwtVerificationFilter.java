package com.zhuang.filter;

import com.zhuang.domain.Payload;
import com.zhuang.domain.SysUser;
import com.zhuang.prop.RsaKeyProperties;
import com.zhuang.utils.JwtUtils;
import com.zhuang.utils.ResponseUtils;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证过滤器
 */
public class JwtVerificationFilter extends BasicAuthenticationFilter {

    private final RsaKeyProperties prop;

    public JwtVerificationFilter(AuthenticationManager authenticationManager, RsaKeyProperties prop) {
        super(authenticationManager);
        this.prop = prop;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            String header = request.getHeader("Authorization");
            if (header == null || !header.startsWith("Bearer ")) {
                //如果token的格式错误，则提示用户非法登录
                chain.doFilter(request, response);
                ResponseUtils.write(response, HttpServletResponse.SC_FORBIDDEN, "用户非法登录！");
            } else {
                //如果token的格式正确，则先要获取到token
                String token = header.replace("Bearer ", "");
                //使用公钥进行解密然后来验证token是否正确
                Payload<SysUser> payload = JwtUtils.getInfoFromToken(token, prop.getPublicKey(), SysUser.class);
                SysUser sysUser = payload.getUserInfo();
                if (sysUser != null) {
                    UsernamePasswordAuthenticationToken authResult = new UsernamePasswordAuthenticationToken(sysUser.getUsername(), null, sysUser.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authResult);
                    chain.doFilter(request, response);
                } else {
                    ResponseUtils.write(response, HttpServletResponse.SC_FORBIDDEN, "用户验证失败！");
                }
            }
        } catch (ExpiredJwtException e) {
            ResponseUtils.write(response, HttpServletResponse.SC_FORBIDDEN, "请您重新登录！");
        }
    }
}