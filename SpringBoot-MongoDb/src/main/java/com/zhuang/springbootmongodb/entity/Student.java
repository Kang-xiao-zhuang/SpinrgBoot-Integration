package com.zhuang.springbootmongodb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * description: Student
 * date: 2023/4/11 14:46
 * author: Zhuang
 * version: 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Data
public class Student implements Serializable {
    @Id// 必须指定id列
    private String studentId;

    private String studentName;

    private Integer studentAge;

    private Double studentScore;

    private Date studentBirthday;
}
