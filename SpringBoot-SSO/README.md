# SpringBoot+SpringSecurity+JWT实现单点登录

## 1. SSO

单点登录（Single Sign-On）是一种身份验证机制，它允许用户使用一组凭据（例如用户名和密码）登录到多个应用程序或系统，而无需为每个应用程序单独提供凭据。这种机制提高了用户体验，简化了登录过程，并提供更好的安全性。

通过使用SSO，用户只需一次登录，就可以访问多个应用程序，而无需重复输入凭据。这不仅节省了时间和精力，还减少了用户忘记密码的风险。此外，SSO还可以提高安全性，因为用户的凭据只需要在一处进行验证，而不是在每个应用程序中进行验证。

实现SSO的方式有多种，其中一种常见的方法是使用标准的身份验证协议，如SAML（Security Assertion Markup Language）或OAuth（Open Authorization）。这些协议允许应用程序之间安全地传递身份验证信息，以实现单点登录功能。



单点登录（SSO）的流程通常包括以下步骤：

1. 用户访问一个需要身份验证的应用程序或系统。
2. 应用程序检测到用户未经身份验证，并将用户重定向到身份提供者（Identity Provider，IdP）。
3. 用户在身份提供者处输入其凭据（例如用户名和密码）进行身份验证。
4. 身份提供者验证用户的凭据，并生成一个身份验证令牌（Assertion）。
5. 身份提供者将身份验证令牌返回给用户的浏览器。
6. 用户的浏览器将身份验证令牌发送回最初的应用程序。
7. 应用程序接收到身份验证令牌，并将其发送给身份提供者进行验证。
8. 身份提供者验证身份验证令牌的有效性，并确认用户的身份。
9. 应用程序接受身份验证，并将用户登录到应用程序或系统中。
10. 用户可以访问应用程序或系统，而无需再次进行身份验证。



## 2. JWT

JWT（JSON Web Token）是一种用于身份验证和授权的开放标准。它是一种轻量级的安全令牌，用于在应用程序和服务之间传递声明信息。

JWT由三部分组成：头部（Header）、载荷（Payload）和签名（Signature）。头部包含描述令牌类型和算法的元数据，载荷包含要传递的声明信息，签名用于验证令牌的真实性和完整性。

JWT的工作流程如下：

1. 用户提供凭据进行身份验证。
2. 服务器验证凭据并生成JWT。
3. 服务器将JWT发送回客户端。
4. 客户端在后续请求中将JWT作为身份验证凭据发送给服务器。
5. 服务器验证JWT的签名并解析其中的声明信息。
6. 服务器根据声明信息进行授权和身份验证。
7. 服务器响应请求并提供相应的资源或服务。

JWT具有以下优点：

- 简洁性：JWT是一种轻量级的令牌，易于传输和处理。
- 自包含性：JWT中包含了所有必要的信息，无需进行数据库查询或其他操作。
- 可扩展性：可以向JWT的载荷中添加自定义声明信息，以满足特定需求。
- 安全性：JWT的签名可以确保令牌的真实性和完整性。

然而，需要注意的是，为了确保JWT的安全性，必须采取适当的措施来保护令牌的传输和存储，以及正确配置和验证签名算法。

![在这里插入图片描述](https://img-blog.csdnimg.cn/a76b1bdcfad24a1dac1dc429a170f3cc.png)



**JWT生成的token的安全性分析：**

从JWT生成的token组成上来看，要想避免token被伪造，主要就得看签名部分了，而签名部分又有三部分组成，其中头部和载荷的base64编码，几乎是透明的，毫无安全性可言，那么最终守护token安全的重担就落在了加入的盐上面了，试想，如果生成token所用的盐与解析token时加入的盐是一样的。岂不是类似于中国人民银行把人民币防伪技术公开了？大家可以用这个盐来解析token，就能用来伪造token。 这时，我们就需要对盐采用非对称加密的方式进行加密，以达到生成token与校验token方所用的盐不一致的安全效果！



> 注意：加盐的意思就是让味道改变，也就是让通过加盐来提高token的复杂度，让token更加安全，这个盐你可以任意指定，全凭自己和项目需求。

## 3. RSA

RSA（Rivest-Shamir-Adleman）是一种非对称加密算法，常用于加密和数字签名。它是由Ron Rivest、Adi Shamir和Leonard Adleman在1977年提出的。

RSA算法基于两个大质数的乘积难解问题，其中一个质数用于生成公钥，另一个质数用于生成私钥。公钥用于加密数据，私钥用于解密数据或生成数字签名。

RSA的工作流程如下：

1. 密钥生成：选择两个大质数p和q，并计算它们的乘积n（n = p * q）。选择一个与(p-1) * (q-1)互质的数e作为公钥。计算一个满足(d * e) % ((p-1) * (q-1)) = 1的数d作为私钥。
2. 加密：将待加密的数据转换为整数m，并使用公钥(n, e)进行加密。加密操作为c = m^e mod n，其中c为加密后的密文。
3. 解密：使用私钥(n, d)对密文c进行解密。解密操作为m = c^d mod n，其中m为解密后的明文。
4. 数字签名：使用私钥(n, d)对待签名的数据进行签名。签名操作为s = m^d mod n，其中s为数字签名。
5. 验证签名：使用公钥(n, e)对签名s进行验证。验证操作为m' = s^e mod n，并比较m'与原始数据m是否相等。

RSA算法的安全性基于大质数分解问题的困难性。要破解RSA，需要大量计算量和时间来分解n，找到p和q的值。

![在这里插入图片描述](https://img-blog.csdnimg.cn/5deaa027a3b645a395dd8bbfe7729798.png)

## 4. 认证思路

### 5.1、分析集中式认证流程

用户认证：使用`UsernamePasswordAuthenticationFilter`过滤器中`attemptAuthentication`方法实现认证功能，该过滤器父类中`successfulAuthentication`方法实现认证成功后的操作。



身份校验：使用`BasicAuthenticationFilter`过滤器中`doFilterInternal`方法验证是否登录，以决定能否进入后续过滤器。

### 5.2、分析分布式认证流程

用户认证：分布式项目多数是前后端分离的架构设计，我们要满足可以接受异步post的认证请求参数，需要修改`UsernamePasswordAuthenticationFilter`过滤器中`attemptAuthentication`方法，让其能够接收请求体。另外，默认`successfulAuthentication`方法在认证通过后，是把用户信息直接放入session就完事了，现在我们需要修改这个方法，在认证通过后生成token并返回给用户。



身份校验： 原来`BasicAuthenticationFilter`过滤器中`doFilterInternal`方法校验用户是否登录，就是看session中是否有用户信息，我们要修改为，验证用户携带的token是否合法，并解析出用户信息，交给SpringSecurity，以便于后续的授权功能可以正常使用。



## 5. 重要代码

认证成功所执行的方法

```java
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
```

验证过滤器

```java
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
```

