package com.zhuang.springbootrabbitmq.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Classname SwaggerConfig
 * @Description Swagger配置类
 * @Date 2021/12/29 17:34
 * @Author by Zhuang
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //     .apis(RequestHandlerSelectors.basePackage("com.zhuang.springbootswaggerui.controller"))
                .paths(PathSelectors.any())
                .build();

    }


    //配置项目基本信息
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("庄康的RabbitMQ项目")
                .description("学习Swagger的Demo")
                .termsOfServiceUrl("https://github.com/Kang-xiao-zhuang")
                //请填写项目联系人信息（名称、网址、邮箱）
                .contact(new Contact("康小庄", "https://itkxz.cn/", "itkxz0830@163.com"))
                //请填写项目版本号
                .version("1.0")
                .build();
    }
}
