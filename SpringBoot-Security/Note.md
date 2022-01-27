# Spring Security 学习笔记

![在这里插入图片描述](https://img-blog.csdnimg.cn/264211ad86344b00acc201899f9a15e5.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

# 1. SpringSecurity 框架简介

## 1.1 概要

Spring 是非常流行和成功的 Java 应用开发框架，Spring Security 正是 Spring 家族中的 成员。Spring Security 基于 Spring 框架，提供了一套 Web 应用安全性的完整解决方案。

正如你可能知道的关于安全方面的两个主要区域是**“认证”**和**“授权”**（或者访问控 制），一般来说，Web 应用的安全性包括**用户认证（Authentication）和用户授权 （Authorization）**两个部分，这两点也是 Spring Security 重要核心功能。

（1）用户认证指的是：验证某个用户是否为系统中的合法主体，也就是说用户能否访问 该系统。用户认证一般要求用户提供用户名和密码。系统通过校验用户名和密码来完成认 证过程。**通俗点说就是系统认为用户是否能登录**

（2）用户授权指的是验证某个用户是否有权限执行某个操作。在一个系统中，不同用户 所具有的权限是不同的。比如对一个文件来说，有的用户只能进行读取，而有的用户可以 进行修改。一般来说，系统会为不同的用户分配不同的角色，而每个角色则对应一系列的 权限。**通俗点讲就是系统判断用户是否有权限去做某些事情。**

## 1.2 历史

“Spring Security 开始于 2003 年年底,““spring 的 acegi 安全系统”。 起因是 Spring 开发者邮件列表中的一个问题,有人提问是否考虑提供一个基于 spring 的安全实现。

Spring Security 以“The Acegi Secutity System for Spring” 的名字始于 2013 年晚些 时候。一个问题提交到 Spring 开发者的邮件列表，询问是否已经有考虑一个机遇 Spring  的安全性社区实现。那时候 Spring 的社区相对较小（相对现在）。实际上 Spring 自己在 2013 年只是一个存在于 ScourseForge 的项目，这个问题的回答是一个值得研究的领 域，虽然目前时间的缺乏组织了我们对它的探索。

考虑到这一点，一个简单的安全实现建成但是并没有发布。几周后，Spring 社区的其他成 员询问了安全性，这次这个代码被发送给他们。其他几个请求也跟随而来。到 2014 年一 月大约有 20 万人使用了这个代码。这些创业者的人提出一个 SourceForge 项目加入是为 了，这是在 2004 三月正式成立。

在早些时候，这个项目没有任何自己的验证模块，身份验证过程依赖于容器管理的安全性 和 Acegi 安全性。而不是专注于授权。开始的时候这很适合，但是越来越多的用户请求额 外的容器支持。容器特定的认证领域接口的基本限制变得清晰。还有一个相关的问题增加 新的容器的路径，这是最终用户的困惑和错误配置的常见问题。

Acegi 安全特定的认证服务介绍。大约一年后，Acegi 安全正式成为了 Spring 框架的子项 目。1.0.0 最终版本是出版于 2006 -在超过两年半的大量生产的软件项目和数以百计的改 进和积极利用社区的贡献。

Acegi 安全 2007 年底正式成为了 Spring 组合项目，更名为"Spring Security"。

**[Spring Security](https://spring.io/projects/spring-security)**

**SpringSecurity 特点：**

- 和 Spring 无缝整合
- 全面的权限控制
- 专门为WEB开发而设计
    - 旧版本不能脱离Web环境使用
    - 新版本对整个框架进行分层抽取，分成了核心模块和Web模块。单独引入核心模块就可以脱离Web环境
- 重量级

# 2. 入门案例

**配置类**

```java
@Configuration
public class SecurityConfigextends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin() // 表单登录
		.and()
		 .authorizeRequests() // 认证配置
		.anyRequest() // 任何请求
		.authenticated();
		// 都需要身份验证
	}
}
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/8ccd165619dc4f98908f434988cf71ca.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

默认的用户名：user

**密码在项目启动的时候在控制台会打印，注意每次启动的时候密码都回发生变化！**

![在这里插入图片描述](https://img-blog.csdnimg.cn/9c4f2b8c34da4ff2b3dcc525f15310d1.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

## 2.1 权限管理中的相关概念

### 2.1.1 主体

英文单词：principal 使用系统的用户或设备或从其他系统远程登录的用户等等。简单说就是谁使用系 统谁就是主体。

### 2.1.2 认证

英文单词：authentication 权限管理系统确认一个主体的身份，允许主体进入系统。简单说就是“主体”证 明自己是谁。 笼统的认为就是以前所做的登录操作。

### 2.1.3 授权

英文单词：authorization 将操作系统的“权力”“授予”“主体”，这样主体就具备了操作系统中特定功 能的能力。

### 2.1.4 添加控制器进行访问

```java
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("hello")
    public String hello() {
        return "hello Security";
    }
}
```

## 2.2 SpringSecurity 基本原理

SpringSecurity 本质是一个过滤器链：

```java
org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter
org.springframework.security.web.context.SecurityContextPersistenceFilter 
org.springframework.security.web.header.HeaderWriterFilter
org.springframework.security.web.csrf.CsrfFilter
org.springframework.security.web.authentication.logout.LogoutFilter 
org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter 
org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter 
org.springframework.security.web.authentication.ui.DefaultLogoutPageGeneratingFilter
org.springframework.security.web.savedrequest.RequestCacheAwareFilter
org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter
org.springframework.security.web.authentication.AnonymousAuthenticationFilter 
org.springframework.security.web.session.SessionManagementFilter 
org.springframework.security.web.access.ExceptionTranslationFilter 
org.springframework.security.web.access.intercept.FilterSecurityInterceptor
```

代码底层流程：重点看三个过滤器： FilterSecurityInterceptor：是一个方法级的权限过滤器, 基本位于过滤链的最底部。

![在这里插入图片描述](https://img-blog.csdnimg.cn/cdc6dc7f479b42d2a20471daab71d040.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

super.beforeInvocation(fi) 表示查看之前的 filter 是否通过。

fi.getChain().doFilter(fi.getRequest(), fi.getResponse());表示真正的调用后台的服务。

****

ExceptionTranslationFilter：是个异常过滤器，用来处理在认证授权过程中抛出的异常

![在这里插入图片描述](https://img-blog.csdnimg.cn/819dfda1133043468a0a5e8ee7c248da.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

UsernamePasswordAuthenticationFilter ：对/login 的 POST 请求做拦截，校验表单中用户 名，密码

![在这里插入图片描述](https://img-blog.csdnimg.cn/9275f333a7304e298dafb969d45098ed.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

## 2.3 UserDetailsService 接口讲解

当什么也没有配置的时候，账号和密码是由 Spring Security 定义生成的。而在实际项目中 账号和密码都是从数据库中查询出来的。 所以我们要通过自定义逻辑控制认证逻辑。

****

如果需要自定义逻辑时，只需要实现 UserDetailsService 接口即可。接口定义如下：

![在这里插入图片描述](https://img-blog.csdnimg.cn/12820b5846d34610accacc21d49528bd.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

**返回值 UserDetails**

这个类是系统默认的用户“主体”

```java
// 表示获取登录用户所有权限
Collection<? extends GrantedAuthority> getAuthorities();
// 表示获取密码
String getPassword();
// 表示获取用户名
String getUsername();
// 表示判断账户是否过期
boolean isAccountNonExpired();
// 表示判断账户是否被锁定
boolean isAccountNonLocked();
// 表示凭证{密码}是否过期
boolean isCredentialsNonExpired();
// 表示当前用户是否可用
boolean isEnabled();
```

**以下是 UserDetails 实现类**

![在这里插入图片描述](https://img-blog.csdnimg.cn/c4f25145fced46258d4a4f1b759836c1.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

以后我们只需要使用 User 这个实体类即可！

![在这里插入图片描述](https://img-blog.csdnimg.cn/bb0829db55ec4d668c07a4e17c016fa9.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

**方法参数 username**

表示用户名。此值是客户端表单传递过来的数据。默认情况下必须叫 username，否则无 法接收。

PasswordEncoder 接口讲解

```java
// 表示把参数按照特定的解析规则进行解析
String encode(CharSequence rawPassword);
// 表示验证从存储中获取的编码密码与编码后提交的原始密码是否匹配。如果密码匹
配，则返回 true；如果不匹配，则返回 false。第一个参数表示需要被解析的密码。第二个
参数表示存储的密码。
boolean matches(CharSequence rawPassword, String encodedPassword);
// 表示如果解析的密码能够再次进行解析且达到更安全的结果则返回 true，否则返回
false。默认返回 false。
default boolean upgradeEncoding(String encodedPassword) {
return false;
}
```

**接口实现类**

![在这里插入图片描述](https://img-blog.csdnimg.cn/fc1f325c172b4490a9b7792e0581449e.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

**BCryptPasswordEncoder** 是 Spring Security 官方推荐的密码解析器，平时多使用这个解析 器。

**BCryptPasswordEncoder** 是对 bcrypt 强散列方法的具体实现。是基于 Hash 算法实现的单 向加密。可以通过 strength 控制加密强度，默认 10.

**查用方法演示**

```java
@Test
public void test01(){
// 创建密码解析器
BCryptPasswordEncoder bCryptPasswordEncoder = new 
BCryptPasswordEncoder();
// 对密码进行加密
String 123 = bCryptPasswordEncoder.encode("123");
// 打印加密之后的数据
System.out.println("加密之后数据：\t"+123);
//判断原字符加密后和加密之前是否匹配
boolean result = bCryptPasswordEncoder.matches("123", 123);
// 打印比较结果
System.out.println("比较结果：\t"+result);
}
```

## 2.4 SpringBoot 对 Security 的自动配置

[Spring Security Reference](https://docs.spring.io/spring-security/site/docs/5.3.4.RELEASE/reference/html5/#servlet-hello)

# 3. SpringSecurity Web 权限方案

## 3.1 设置登录系统的账号、密码

**在 application.properties**

```properties
spring.security.user.name=123
spring.security.user.password=123
```

**编写类实现接口**

```java
package com.zhuang.config;
@Configuration
public class SecurityConfig {
	// 注入 PasswordEncoder 类到 spring 容器中
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
```

```java
package com.zhuang.service;
@Service
public class LoginService implements UserDetailsService {
	@Override
	public UserDetails loadUserByUsername(String username) throws 
	UsernameNotFoundException {
		// 判断用户名是否存在
		if (!"admin".equals(username)){
			throw new UsernameNotFoundException("用户名不存在！");
		}
		// 从数据库中获取的密码 zhuang 的密文
		String pwd = 
		"$2a$10$2R/M6iU3mCZt3ByG7kwYTeeW0w7/UqdeXrb27zkBIizBvAven0/na";
		// 第三个参数表示权限
		return new User(username,pwd, 
		AuthorityUtils.commaSeparatedStringToAuthorityList("admin,"));
	}
}
```

**实现数据库认证来完成用户登录**

完成自定义登录

```sql
create table users(
 id bigint primary key auto_increment,
username varchar(20) unique not null,
password varchar(100)
);
-- 密码 atguigu
insert into users values(1,'张
san','$2a$10$2R/M6iU3mCZt3ByG7kwYTeeW0w7/UqdeXrb27zkBIizBvAven0/na');
-- 密码 atguigu
insert into users values(2,'李
si','$2a$10$2R/M6iU3mCZt3ByG7kwYTeeW0w7/UqdeXrb27zkBIizBvAven0/na');
create table role(
id bigint primary key auto_increment,
name varchar(20)
);
insert into role values(1,'管理员');
insert into role values(2,'普通用户');
create table role_user(
uid bigint,
rid bigint
);
insert into role_user values(1,1);
insert into role_user values(2,2);
create table menu(
id bigint primary key auto_increment,
name varchar(20),
url varchar(100),
parentid bigint,
permission varchar(20)
);
insert into menu values(1,'系统管理','',0,'menu:system');
insert into menu values(2,'用户管理','',0,'menu:user');
create table role_menu(
mid bigint,
rid bigint
);
insert into role_menu values(1,1);
insert into role_menu values(2,1);
insert into role_menu values(2,2);
```

**依赖**

```xml
<dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <!--SpringBoot启动器-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.4.0</version>
        </dependency>
        <!--mybatis-plus依赖-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus</artifactId>
            <version>3.4.2</version>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.1.22</version>
            <scope>compile</scope>
        </dependency>
    </dependencies>
```

**实体类**

```java
package com.zhuang.springbootsecurity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Users {
    private Integer id;
    private String username;
    private String password;
}
```

**整合MybatisPlus整合**

```java
@Repository
public interface UsersMapper extends BaseMapper<Users> {
}
配置文件添加数据库配置 
#mysql 数据库连接
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver 
spring.datasource.url=jdbc:mysql://localhost:3306/demo?serverTimezone=GMT%2B8 
spring.datasource.username=root 
spring.datasource.password=root
```

**登录实现类**

```java
package com.zhuang.springbootsecurity.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhuang.springbootsecurity.entity.Users;
import com.zhuang.springbootsecurity.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userDetailsService")
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UsersMapper usersMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // mybatis-plus查询数据库
        //LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //userLambdaQueryWrapper.eq(User::getUsername, username);
        QueryWrapper<Users> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", username);
        Users user = usersMapper.selectOne(userQueryWrapper);
        //判断
        if (user == null) {
            // 数据库没有 认证失败
            throw new UsernameNotFoundException("用户名不存在");
        }

        List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("admins,ROLE_sale");
        return new User(user.getUsername(), new BCryptPasswordEncoder().encode(user.getPassword()), auths);
    }
}
```

**自定义登录页面跳转**

```java
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
```

**login.html**

```html
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/html" xmlns="http://www.w3.org/1999/html">
<head>
    <meta charset="UTF-8">
    <title>登录页面</title>
</head>
<body>
<form action="/user/login" method="post">
    用户名:<input type="text" name="username"/><br/>
    密码：<input type="password" name="password"/><br/>
    记住我：<input type="checkbox" name="remember-me" title="记住密码"><br>
    <input type="submit" value="提交"/>
</form>
</body>
</html>
```

**unauth.html**

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>没有访问权限！</h1>
</body>
</html>
```

**注意：页面提交方式必须为 post 请求，所以上面的页面不能使用，用户名，密码必须为 username,password**

**原因：**

在执行登录的时候会走一个过滤器 UsernamePasswordAuthenticationFilter

![在这里插入图片描述](https://img-blog.csdnimg.cn/3f9477f95b794700914136518b5fcbfd.png)

## 3.2 基于角色或权限进行访问控制

### 3.2.1 hasAuthority 方法

**修改配置类**

如果当前的主体具有指定的权限，则返回 true,否则返回 false

![在这里插入图片描述](https://img-blog.csdnimg.cn/93f5dd43bc8546d5a802ea4fb8660a76.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

**添加一个控制器**

```java
 @GetMapping("index")
    public String index() {
        return "hello index";
    }
```

**给用户登录主体赋予权限**

![在这里插入图片描述](https://img-blog.csdnimg.cn/ebbc16d08bc64a41a7d35469a8b88117.png)

**测试成功**

### 3.2.2 hasAnyAuthority 方法

如果当前的主体有任何提供的角色（给定的作为一个逗号分隔的字符串列表）的话，返回 true.

### 3.2.3  hasRole 方法

如果用户具备给定角色就允许访问,否则出现 403。 如果当前主体具有指定的角色，则返回 true

![在这里插入图片描述](https://img-blog.csdnimg.cn/16973f49c6e148e88791876315dd5788.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

### 3.2.4 hasAnyRole

表示用户具备任何一个条件都可以访问。

**给用户添加角色：**

![在这里插入图片描述](https://img-blog.csdnimg.cn/9f6b036dbf8f4cdaa83e6e14e20004fe.png)

**修改配置文件：**

![在这里插入图片描述](https://img-blog.csdnimg.cn/a11aa97306af49a9aa399adcfd85f839.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

## 3.3  注解使用

### 3.3.1 @Secured

判断是否具有角色，另外需要注意的是这里匹配的字符串需要添加前缀“ROLE_“。 使用注解先要开启注解功能！

```java
@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled=true)
public class DemosecurityApplication {
public static void main(String[] args) {
 	SpringApplication.run(DemosecurityApplication.class, args);
 	}
}
```

**在控制器方法上添加注解**

```java
// 测试注解：
@RequestMapping("testSecured")
@ResponseBody
@Secured({"ROLE_normal","ROLE_admin"})
public String helloUser() {
return "hello,user";
}
@Secured({"ROLE_normal","ROLE_管理员"})
```

登录之后直接访问：http://localhost:8090/testSecured 控制器 将上述的角色改为 **@Secured({"ROLE_normal","ROLE_管理员"})**即可访问

### 3.3.2 @PreAuthorize

先开启注解

```java
@EnableGlobalMethodSecurity(prePostEnabled = true)
```

@PreAuthorize：注解适合进入方法前的权限验证， @PreAuthorize 可以将登录用 户的 roles/permissions 参数传到方法中

```java
@RequestMapping("/preAuthorize")
@ResponseBody
//@PreAuthorize("hasRole('ROLE_管理员')")
@PreAuthorize("hasAnyAuthority('menu:system')")
public String preAuthorize(){
 System.out.println("preAuthorize");
return "preAuthorize";
}
```

### 3.3.3 @PostAuthorize

@PostAuthorize 注解使用并不多，在方法执行后再进行权限验证，适合验证带有返回值 的权限.

```java
@RequestMapping("/testPostAuthorize")
@ResponseBody
@PostAuthorize("hasAnyAuthority('menu:system')")
public String preAuthorize(){
 System.out.println("test--PostAuthorize");
return "PostAuthorize";
}
```

### 3.3.4 @PostFilter

@PostFilter ：权限验证之后对数据进行过滤 留下用户名是 admin1 的数据

表达式中的 filterObject 引用的是方法返回值 List 中的某一个元素

```java
@RequestMapping("getAll")
@PreAuthorize("hasRole('ROLE_管理员')")
@PostFilter("filterObject.username == 'admin1'")
@ResponseBody
public List<UserInfo> getAllUser(){
     ArrayList<UserInfo> list = new ArrayList<>();
     list.add(new UserInfo(1l,"admin1","6666"));
     list.add(new UserInfo(2l,"admin2","888"));
    return list;
}
```

### 3.3.5 @PreFilter

@PreFilter: 进入控制器之前对数据进行过滤

```java
@RequestMapping("getTestPreFilter")
@PreAuthorize("hasRole('ROLE_管理员')")
@PreFilter(value = "filterObject.id%2==0")
@ResponseBody
public List<UserInfo> getTestPreFilter(@RequestBody List<UserInfo> list){
     list.forEach(t-> {
     System.out.println(t.getId()+"\t"+t.getUsername());
     });
    return list;
}
```

## 3.4 基于数据库的记住我

```sql
CREATE TABLE `persistent_logins` (
 `username` varchar(64) NOT NULL,
 `series` varchar(64) NOT NULL,
 `token` varchar(64) NOT NULL,
 `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE 
CURRENT_TIMESTAMP,
 PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

**编写配置类**

```java
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
```

**添加复选框**

```java
记住我：<input type="checkbox"name="remember-me"title="记住密码"/><br/>
```

## 3.5 设置有效期

默认 2 周时间。但是可以通过设置状态有效时间，即使项目重新启动下次也可以正常登 录。

```java
tokenValiditySeconds(60) //60秒
```

# 4. SpringSecurity 原理总结

## 4.1  SpringSecurity 的过滤器介绍

SpringSecurity 采用的是责任链的设计模式，它有一条很长的过滤器链。现在对这条过滤 器链的 15 个过滤器进行说明:

1. WebAsyncManagerIntegrationFilter：将 Security 上下文与 Spring Web 中用于 处理异步请求映射的 WebAsyncManager 进行集成。

2. SecurityContextPersistenceFilter：在每次请求处理之前将该请求相关的安全上 下文信息加载到 SecurityContextHolder 中，然后在该次请求处理完成之后，将 SecurityContextHolder 中关于这次请求的信息存储到一个“仓储”中，然后将 SecurityContextHolder 中的信息清除，例如在 Session 中维护一个用户的安全信 息就是这个过滤器处理的。

3. HeaderWriterFilter：用于将头信息加入响应中。

4. CsrfFilter：用于处理跨站请求伪造

5. LogoutFilter：用于处理退出登录。

6. UsernamePasswordAuthenticationFilter：用于处理基于表单的登录请求，从表单中 获取用户名和密码。默认情况下处理来自 /login 的请求。从表单中获取用户名和密码 时，默认使用的表单 name 值为 username 和 password，这两个值可以通过设置这个 过滤器的 usernameParameter 和 passwordParameter 两个参数的值进行修改。

7. DefaultLoginPageGeneratingFilter：如果没有配置登录页面，那系统初始化时就会 配置这个过滤器，并且用于在需要进行登录时生成一个登录表单页面。

8. BasicAuthenticationFilter：检测和处理 http basic 认证。

9. RequestCacheAwareFilter：用来处理请求的缓存

10. SecurityContextHolderAwareRequestFilter：主要是包装请求对象 request

11. AnonymousAuthenticationFilter：检测 SecurityContextHolder 中是否存在 Authentication 对象，如果不存在为其提供一个匿名 Authentication

12. AnonymousAuthenticationFilter：检测 SecurityContextHolder 中是否存在 Authentication 对象，如果不存在为其提供一个匿名 Authentication

13. ExceptionTranslationFilter：处理 AccessDeniedException 和 AuthenticationException 异常。

14. FilterSecurityInterceptor：可以看做过滤器链的出口。

15. RememberMeAuthenticationFilter：当用户没有登录而直接访问资源时, 从 cookie  里找出用户的信息, 如果 Spring Security 能够识别出用户提供的 remember me cookie,  用户将不必填写用户名和密码, 而是直接登录进入系统，该过滤器默认不开启

## 4.2 SpringSecurity 基本流程

**Spring Security** 采取过滤链实现认证与授权，只有当前过滤器通过，才能进入下一个 过滤器：

![在这里插入图片描述](https://img-blog.csdnimg.cn/b085de323ead4ed19be5eeafec74f08b.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

绿色部分是认证过滤器，需要我们自己配置，可以配置多个认证过滤器。认证过滤器可以 使用 Spring Security 提供的认证过滤器，也可以自定义过滤器（例如：短信验证）。认 证过滤器要在 configure(HttpSecurity http)方法中配置，没有配置不生效。下面会重 点介绍以下三个过滤器：

UsernamePasswordAuthenticationFilter 过滤器：该过滤器会拦截前端提交的 POST 方式 的登录表单请求，并进行身份认证。

ExceptionTranslationFilter 过滤器：该过滤器不需要我们配置，对于前端提交的请求会 直接放行，捕获后续抛出的异常并进行处理（例如：权限访问限制）。

FilterSecurityInterceptor 过滤器：该过滤器是过滤器链的最后一个过滤器，根据资源 权限配置来判断当前请求是否有权限访问对应的资源。如果访问受限会抛出相关异常，并 由 ExceptionTranslationFilter 过滤器进行捕获和处理。

## 4.3 SpringSecurity 认证流程

认证流程是在 UsernamePasswordAuthenticationFilter 过滤器中处理的，具体流程如下 所示：

![在这里插入图片描述](https://img-blog.csdnimg.cn/f01d5cc48c26411184a5654d01efece3.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

### 4.3.1 UsernamePasswordAuthenticationFilter 源码

当前端提交的是一个 POST 方式的登录表单请求，就会被该过滤器拦截，并进行身份认 证。该过滤器的 doFilter() 方法实现在其抽象父类

`AbstractAuthenticationProcessingFilter` 中，查看相关源码：

![在这里插入图片描述](https://img-blog.csdnimg.cn/51eda02071a044c0abb4bac591f80fae.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

**第二 过程调用了 UsernamePasswordAuthenticationFilter 的 attemptAuthentication() 方法，源码如下**

![在这里插入图片描述](https://img-blog.csdnimg.cn/a0ed53cc442b44ba91db7f38716f4583.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

上述的（3）过程创建的 `UsernamePasswordAuthenticationToken `是 `Authentication `接口的实现类，该类有两个构造器，一个用于封装前端请求传入的未认 证的用户信息，一个用于封装认证成功后的用户信息：

![在这里插入图片描述](https://img-blog.csdnimg.cn/4d9e7548ea8a4ab48894b60b4872074c.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

**Authentication 接口的实现类用于存储用户认证信息，查看该接口具体定义**

![在这里插入图片描述](https://img-blog.csdnimg.cn/39d5746097ba4fd48c992ba8c9a7bd32.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

## 4.4 ProviderManager 源码

`UsernamePasswordAuthenticationFilter `过滤器的 `attemptAuthentication`() 方法的（5）过程将未认证的 `Authentication `对象传入 `ProviderManager `类的 `authenticate()` 方法进行身份认证

ProviderManager 是 AuthenticationManager 接口的实现类，该接口是认证相关的核心接 口，也是认证的入口。在实际开发中，我们可能有多种不同的认证方式，例如：用户名+ 密码、邮箱+密码、手机号+验证码等，而这些认证方式的入口始终只有一个，那就是 AuthenticationManager。在该接口的常用实现类 ProviderManager 内部会维护一个 List列表，存放多种认证方式，实际上这是委托者模式 （Delegate）的应用。每种认证方式对应着一个 AuthenticationProvider， AuthenticationManager 根据认证方式的不同（根据传入的 Authentication 类型判断）委托 对应的 AuthenticationProvider 进行用户认证。

![在这里插入图片描述](https://img-blog.csdnimg.cn/0963e2c021584f42856277bd37f73d05.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

![在这里插入图片描述](https://img-blog.csdnimg.cn/bad500245bdb4e04978288aab6bddc60.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

![在这里插入图片描述](https://img-blog.csdnimg.cn/8a65a0a633324fa48d05f63dc4638e15.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

![在这里插入图片描述](https://img-blog.csdnimg.cn/67904e7c7a174931b63ed652df6295aa.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

上述认证成功之后的（6）过程，调用 `CredentialsContainer `接口定义的 `eraseCredentials()` 方法去除敏感信息。查看 `UsernamePasswordAuthenticationToken `实现的 `eraseCredentials()` 方法，该方 法实现在其父类中：

![在这里插入图片描述](https://img-blog.csdnimg.cn/cfd74100eefc48c59c263d5154080d3d.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

## 4.5 认证成功/失败处理

UsernamePasswordAuthenticationFilter 过滤器的 doFilter() 方法，查看认证成 功/失败的处理：

![在这里插入图片描述](https://img-blog.csdnimg.cn/e5d84b01d8c94bb7b90858dea5faaf22.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

![在这里插入图片描述](https://img-blog.csdnimg.cn/db4f4f65dbcc4f33a672cc1b234dd60c.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

![在这里插入图片描述](https://img-blog.csdnimg.cn/343e8f2519d64ecd915efb42f8d03f01.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

![在这里插入图片描述](https://img-blog.csdnimg.cn/9ec61bf6feb9431abcef32ca1315a0ae.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

## 4.6 SpringSecurity 权限访问流程

ExceptionTranslationFilter 过滤器和 FilterSecurityInterceptor 过滤器进行介绍

### 4.6.1 ExceptionTranslationFilter 过滤器

该过滤器是用于处理异常的，不需要我们配置，对于前端提交的请求会直接放行，捕获后 续抛出的异常并进行处理（例如：权限访问限制）。具体源码如下：

![在这里插入图片描述](https://img-blog.csdnimg.cn/e7135c79f290477d9876ca380f697686.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

### 4.6.2  FilterSecurityInterceptor 过滤器

FilterSecurityInterceptor 是过滤器链的最后一个过滤器，该过滤器是过滤器链 的最后一个过滤器，根据资源权限配置来判断当前请求是否有权限访问对应的资源。如果 访问受限会抛出相关异常，最终所抛出的异常会由前一个过滤器 ExceptionTranslationFilter 进行捕获和处理。具体源码如下：

![在这里插入图片描述](https://img-blog.csdnimg.cn/4bd4ee8d2f9b4ffaa69e48f8c40f8d53.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

需要注意，Spring Security 的过滤器链是配置在 SpringMVC 的核心组件 DispatcherServlet 运行之前。也就是说，请求通过 Spring Security 的所有过滤器， 不意味着能够正常访问资源，该请求还需要通过 SpringMVC 的拦截器链

### 4.6.3 SpringSecurity 请求间共享认证信息

一般认证成功后的用户信息是通过 Session 在多个请求之间共享，那么 Spring  Security 中是如何实现将已认证的用户信息对象 Authentication 与 Session 绑定的进行 具体分析

![在这里插入图片描述](https://img-blog.csdnimg.cn/5e4c9c96a1cd45f79ec028c4d160f1b3.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

认证成功的处理方法 successfulAuthentication()

![在这里插入图片描述](https://img-blog.csdnimg.cn/82474fd73a354a3aa185610ccbb0809a.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

- 查 看 SecurityContext 接 口 及 其 实 现 类 SecurityContextImpl ， 该 类 其 实 就 是 对 Authentication 的封装：
- 查 看 SecurityContextHolder 类 ， 该 类 其 实 是 对 ThreadLocal 的 封 装 ， 存 储 SecurityContext 对象：

![在这里插入图片描述](https://img-blog.csdnimg.cn/bc2088f043704249b0cbdb1762ca4352.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

![在这里插入图片描述](https://img-blog.csdnimg.cn/36f832e6f2934a0e884e0f22f781277a.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

![在这里插入图片描述](https://img-blog.csdnimg.cn/1c0771f6cd6e4adca9a1e8c37099b54b.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

![在这里插入图片描述](https://img-blog.csdnimg.cn/6688e313134e47abb0b7f6ac233c83fa.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)

### 4.6.4 SecurityContextPersistenceFilter 过滤器

前面提到过，在 UsernamePasswordAuthenticationFilter 过滤器认证成功之 后，会在认证成功的处理方法中将已认证的用户信息对象 Authentication 封装进 SecurityContext，并存入 SecurityContextHolder。 之后，响应会通过 SecurityContextPersistenceFilter 过滤器，该过滤器的位置 在所有过滤器的最前面，请求到来先进它，响应返回最后一个通过它，所以在该过滤器中 处理已认证的用户信息对象 Authentication 与 Session 绑定。

认证成功的响应通过 SecurityContextPersistenceFilter 过滤器时，会从 SecurityContextHolder 中取出封装了已认证用户信息对象 Authentication 的 SecurityContext，放进 Session 中。当请求再次到来时，请求首先经过该过滤器，该过滤 器会判断当前请求的 Session 是否存有 SecurityContext 对象，如果有则将该对象取出再次 放入 SecurityContextHolder 中，之后该请求所在的线程获得认证用户信息，后续的资源访 问不需要进行身份认证；当响应再次返回时，该过滤器同样从 SecurityContextHolder 取出 SecurityContext 对象，放入 Session 中。具体源码:

![在这里插入图片描述](https://img-blog.csdnimg.cn/78bdef74379e4487a46e879119b305cc.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA5bq35bCP5bqE,size_20,color_FFFFFF,t_70,g_se,x_16)