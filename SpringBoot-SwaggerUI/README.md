# Swagger学习笔记

![在这里插入图片描述](https://img-blog.csdnimg.cn/709dfc9daeda4319824121a4c83c6e3f.png)

## 1、Swagger简介

**前后端分离**

- 前端 -> 前端控制层、视图层
- 后端 -> 后端控制层、服务层、数据访问层
- 前后端通过API进行交互
- 前后端相对独立且松耦合

**产生的问题**

- 前后端集成，前端或者后端无法做到“及时协商，尽早解决”，最终导致问题集中爆发

**解决方案**

- 首先定义schema [ 计划的提纲 ]，并实时跟踪最新的API，降低集成风险

**Swagger**

- 号称世界上最流行的API框架
- Restful Api 文档在线自动生成器 => API 文档 与API 定义同步更新
- 直接运行，在线测试API
- 支持多种语言 （如：Java，PHP等）
- 官网：https://swagger.io/

## 2、SpringBoot集成

**SpringBoot集成Swagger => springfox**，两个jar包

- Springfox-swagger2
- swagger-springmvc

**使用Swagger**

要求：jdk 1.8 + 否则swagger2无法运行

步骤：
1. 新建一个SpringBoot-web项目
2. 添加Maven依赖

```xml
<!--        Swagger依赖-->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.9.2</version>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>2.9.2</version>
        </dependency>
```

3. 编写HelloController，测试确保运行成功！

4. 要使用Swagger，我们需要编写一个配置类-SwaggerConfig来配置 Swagger

```java
@Configuration
@EnableSwagger2
public class SwaggerUIConfig {
}
```

5. 访问测试 ：http://localhost:8080/swagger-ui.html ，可以看到swagger的界面；

![在这里插入图片描述](https://img-blog.csdnimg.cn/64e97d53c9024d6bb2779144f0a2f4ec.png)

## 3、配置Swagger

1. Swagger实例Bean是Docket，所以通过配置Docket实例来配置Swaggger。

```java
    @Bean //配置docket以配置Swagger具体参数
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2);
    }
```

2. 可以通过apiInfo()属性配置文档信息

```java
    //配置项目基本信息
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("庄康的项目")
                .description("学习Swagger的Demo")
                .termsOfServiceUrl("https://github.com/Kang-xiao-zhuang")
                //请填写项目联系人信息（名称、网址、邮箱）
                .contact(new Contact("康小庄", "https://itkxz.cn/", "itkxz0830@163.com"))
                //请填写项目版本号
                .version("1.0")
                .build();
    }
```

3. Docket 实例关联上 apiInfo()

```java
@Bean
public Docket docket() {
	return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo());
}
```

4. 重启项目，访问测试 http://localhost:8080/swagger-ui.html 看下效果；

## 4、配置扫描接口

1. 构建Docket时通过select()方法配置怎么扫描接口。

```java
    //配置docket以配置Swagger具体参数
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zhuang.springbootswaggerui.controller"))
                .paths(PathSelectors.any())
                .build();

    }
```

2. 重启项目测试，由于我们配置根据包的路径扫描接口，所以我们只能看到一个类

3. 除了通过包路径配置扫描接口外，还可以通过配置其他方式扫描接口，这里注释一下所有的配置方式：

```java
any() // 扫描所有，项目中的所有接口都会被扫描到
none() // 不扫描接口
// 通过方法上的注解扫描，如withMethodAnnotation(GetMapping.class)只扫描get请求
withMethodAnnotation(final Class<? extends Annotation> annotation)
// 通过类上的注解扫描，如.withClassAnnotation(Controller.class)只扫描有
controller注解的类中的接口
withClassAnnotation(final Class<? extends Annotation> annotation)
basePackage(final String basePackage) // 根据包路径扫描接口
```

4. 除此之外，我们还可以配置接口扫描过滤：

```java
    //配置docket以配置Swagger具体参数
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zhuang.springbootswaggerui.controller"))
                // 配置如何通过path过滤,即这里只扫描请求以/zhuang开头的接口
				.paths(PathSelectors.ant("/zhuang/**"))
                .build();

    }
```

5. 这里的可选值还有

```java
any() // 任何请求都扫描
none() // 任何请求都不扫描
regex(final String pathRegex) // 通过正则表达式控制
ant(final String antPattern) // 通过ant()控制
```

## 5、配置开关Swagger

1. 通过enable()方法配置是否启用swagger，如果是false，swagger将不能在浏览器中访问了

```java
    //配置docket以配置Swagger具体参数
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(false) //配置是否启用Swagger，如果是false，在浏览器将无法访问
                .select() // 通过.select()方法，去配置扫描接口,RequestHandlerSelectors配置如何扫描接口
                .apis(RequestHandlerSelectors.basePackage("com.zhuang.springbootswaggerui.controller"))
                .paths(PathSelectors.any())
                .build();

    }
```

## 6、配置API分组

1. 如果没有配置分组，默认是default。通过groupName()方法即可配置分组：

```java
@Bean
public Docket docket(Environment environment) {
    return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
    .groupName("hello") // 配置分组
    // 省略配置....
}
```

2. 重启项目查看分组

3. 如何配置多个分组？配置多个分组只需要配置多个docket即可：

```java
@Bean
public Docket docket1() {
	return new Docket(DocumentationType.SWAGGER_2).groupName("group1");
}
@Bean
public Docket docket2() {
	return new Docket(DocumentationType.SWAGGER_2).groupName("group2");
}
@Bean
public Docket docket3() {
	return new Docket(DocumentationType.SWAGGER_2).groupName("group3");
}
```

4. 重启项目查看

## 7、实体配置

1. 新建一个实体类

```java
@Data
@ApiModel(value = "User类型", description = "这表示一个用户实体")
public class User {
    @ApiModelProperty(name = "name", value = "名称", required = true)
    private String name;
    @ApiModelProperty(name = "age", value = "年龄", required = true)
    private Integer age;
}
```

2. 只要这个实体在请求接口的返回值上（即使是泛型），都能映射到实体项中：

```java
@RequestMapping("/getUser")
    public User getUser() {
        return new User("康小庄", 18);
    }
```

3. 重启查看测试

![在这里插入图片描述](https://img-blog.csdnimg.cn/5a6b9395098e4cb6998458567bdeb082.png)

注：并不是因为@ApiModel这个注解让实体显示在这里了，而是只要出现在接口方法的返回值上的实体都会显示在这里，而@ApiModel和@ApiModelProperty这两个注解只是为实体添加注释的。

@ApiModel为类添加注释
@ApiModelProperty为类属性添加注释

## 8、常用注解

Swagger的所有注解定义在io.swagger.annotations包下

下面列一些经常用到的，未列举出来的可以另行查阅说明：

| Swagger注解                                            | 简单说明                                             |
| ------------------------------------------------------ | ---------------------------------------------------- |
| @Api(tags = "xxx模块说明")                             | 作用在模块类上                                       |
| @ApiOperation("xxx接口说明")                           | 作用在接口方法上                                     |
| @ApiModel("xxxPOJO说明")                               | 作用在模型类上：如VO、BO                             |
| @ApiModelProperty(value = "xxx属性说明",hidden = true) | 作用在类方法和属性上，hidden设置为true可以隐藏该属性 |
| @ApiParam("xxx参数说明")                               | 作用在参数、方法和字段上，类似@ApiModelProperty      |

我们也可以给请求的接口配置一些注释

```java
/**
     * 第一种描述方式，使用注解：@ApiOperation、@ApiParam
     *
     * @param name 姓名
     * @param age  年龄
     * @return 字符串
     */
    @GetMapping("/hello1")
    // value：描述该方法的作用，notes：描述该方法的备注信息
    @ApiOperation(value = "输出用户信息", notes = "返回一个拼接的字符串")
    public String hello1(
            // name：参数名称，value：参数说明，required：是否必填
            @ApiParam(name = "name", value = "名称", required = true) @RequestParam("name") String name,
            @ApiParam(name = "age", value = "年龄", required = true) @RequestParam("age") Integer age) {
        return name + ":" + age;
    }

    /**
     * 第二种描述方式，使用注解：@ApiOperation、@ApiImplicitParams、@ApiImplicitParam
     *
     * @param name 姓名
     * @param age  年龄
     * @return 字符串
     */
    @GetMapping("/hello2")
    @ApiOperation(value = "输出用户信息", notes = "返回一个拼接的字符串")
    //该注解用于在方法上描述该方法的参数
    @ApiImplicitParams({
            // name：参数名称，value：参数说明，required：是否必填
            @ApiImplicitParam(name = "name", value = "名称", required = true),
            @ApiImplicitParam(name = "age", value = "名称", required = true)
    })
    public String hello2(@RequestParam("name") String name, @RequestParam("age") Integer age) {
        return name + ":" + age;
    }

    @PostMapping("/hello3")
    @ApiOperation(value = "输出用户信息", notes = "返回一个对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名称", required = true),
            @ApiImplicitParam(name = "age", value = "名称", required = true)
    })

    public User hello3(@RequestParam("name") String name, @RequestParam("age") Integer age) {
        User user = new User();
        user.setName(name);
        user.setAge(age);
        return user;
    }
```

这样的话，可以给一些比较难理解的属性或者接口，增加一些配置信息，让人更容易阅读！
相较于传统的Postman或Curl方式测试接口，使用swagger简直就是傻瓜式操作，不需要额外说明文档(写得好本身就是文档)而且更不容易出错，只需要录入数据然后点击Execute，如果再配合自动化框架，可以说基本就不需要人为操作了。

## 9、其他皮肤

我们可以导入不同的包实现不同的皮肤定义：

1、默认的 访问 http://localhost:8080/swagger-ui.html

```xml
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.9.2</version>
</dependency>
```

2、bootstrap-ui 访问 http://localhost:8080/doc.html

```xml
<!-- 引入swagger-bootstrap-ui包 /doc.html-->
<dependency>
    <groupId>com.github.xiaoymin</groupId>
    <artifactId>swagger-bootstrap-ui</artifactId>
    <version>1.9.1</version>
</dependency>
```

3、Layui-ui 访问 http://localhost:8080/docs.html

```xml
<!-- 引入swagger-ui-layer包 /docs.html-->
<dependency>
    <groupId>com.github.caspar-chen</groupId>
    <artifactId>swagger-ui-layer</artifactId>
    <version>1.1.3</version>
</dependency>
```

4、mg-ui 访问 http://localhost:8080/document.html

```xml
<!-- 引入swagger-ui-layer包 /document.html-->
<dependency>
    <groupId>com.zyplayer</groupId>
    <artifactId>swagger-mg-ui</artifactId>
    <version>1.0.6</version>
</dependency>
```

