package com.zhuang.springbootjdbc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
/**
 * @Classname Student
 * @Description Student实体
 * @Date 2021/12/09 17:19
 * @Author by Zhuang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Student {
    private Integer id;

    private String name;

    private Integer age;

}
