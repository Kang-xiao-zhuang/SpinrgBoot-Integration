package com.zhuang.springbootelasticsearch.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Content {
    private String src;

    private String price;

    private String name;
}
