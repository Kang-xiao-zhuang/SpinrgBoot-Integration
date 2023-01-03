package com.zhuang.springbootswaggerui.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname User
 * @Description User实体
 * @Date 2021/12/10 23:06
 * @Author by dell
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "User类型", description = "这表示一个用户实体")
public class User {
    @ApiModelProperty(name = "name", value = "名称", required = true)
    private String name;
    @ApiModelProperty(name = "age", value = "年龄", required = true)
    private Integer age;
}
