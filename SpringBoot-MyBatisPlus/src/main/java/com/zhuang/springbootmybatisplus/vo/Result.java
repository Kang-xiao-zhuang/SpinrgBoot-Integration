package com.zhuang.springbootmybatisplus.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Result 返回操作的状态码
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Result {

    private Boolean success;
    private Object data;
    private String message;

}
