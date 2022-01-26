package com.zhuang.springbootsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class SecurityConfigTest extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    // 注入数据源
    @Autowired
    private DataSource dataSource;

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        //jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 退出
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/test/hello").permitAll();
        // 配置没有权限访问跳转自定义页面
        http.exceptionHandling().accessDeniedPage("/unauth.html");
        http.formLogin()
                .loginPage("/login.html") //自定义登录页面
                .loginProcessingUrl("/user/login") // 登录访问路径
                .defaultSuccessUrl("/success.html") // 登录成功之后跳转路径
                .permitAll()
                .and()
                .authorizeRequests().antMatchers("/", "/test/hello", "/user/login").permitAll()
                //.antMatchers("/test/index").hasAuthority("admins") //只有管理员
                //.antMatchers("/test/index").hasAnyAuthority("admins,manager") // 多个角色权限
                .antMatchers("/test/index").hasRole("sale")//角色管理
                .and().rememberMe().tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(60) //60秒
                .userDetailsService(userDetailsService)
                .and().csrf().disable();
    }
}
