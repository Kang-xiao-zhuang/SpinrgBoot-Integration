package com.zhuang.springbootswaggerui.controller;

import com.zhuang.springbootswaggerui.entity.User;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname HelloController
 * @Description 测试SwaggerUI的接口
 * @Date 2021/12/10 23:06
 * @Author by dell
 */
@RestController
//该注解用于描述当前控制器的作用
@Api(tags = "Hello控制器")
public class HelloController {
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
}
