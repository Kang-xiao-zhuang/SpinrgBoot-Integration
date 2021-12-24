package com.zhuang.springbootelasticsearch.entity;

import lombok.*;
/**
 * @Classname Content
 * @Description Content实体
 * @Date 2021/12/21 19:21
 * @Author by dell
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Content {
    private String src;

    private String price;

    private String name;
}
