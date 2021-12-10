package com.zhuang.springbootjdbc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
/**
 * @Classname Edu
 * @Description Edu实体
 * @Date 2021/12/09 17:19
 * @Author by Zhuang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Edu {
    private static final long serialVersionUID = 1L;

    private String name;

    private String lasteventname;

    private String lasteventtimekey;

    private Date lasteventtime;

    private String lasteventuser;

    private String lasteventcomment;

    private String description;

    private String num;
}
