package com.zhuang.springbootmongodb.service;

import com.zhuang.springbootmongodb.entity.Student;

import java.util.List;

/**
 * description: StudentService
 * date: 2023/4/11 15:05
 * author: Zhuang
 * version: 1.0
 */
public interface StudentService {
    //    增加一位学生
    void addOneStudent(Student student);

    //    根据id删除一位学生
    void deleteOneStudentByStudentId(String studentId);

    //    修改一位学生的信息
    void updateOneStudent(Student student);

    //    根据主键id获取一名学生
    Student getOneStudentByStudentId(String studentId);

    //    获取全部学生
    List<Student> getAllStudent();
}
