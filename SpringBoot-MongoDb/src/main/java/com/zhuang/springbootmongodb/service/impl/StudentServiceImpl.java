package com.zhuang.springbootmongodb.service.impl;

import com.zhuang.springbootmongodb.entity.Student;
import com.zhuang.springbootmongodb.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * description: StudentServiceImpl
 * date: 2023/4/11 15:06
 * author: Zhuang
 * version: 1.0
 */
@Service
public class StudentServiceImpl implements StudentService {

    // 使用MongoTemplate模板类实现数据库操作
    @Autowired
    private MongoTemplate mongoTemplate;

    // 增加一位学生
    public void addOneStudent(Student student) {
        mongoTemplate.save(student);
    }

    // 根据id删除一位学生
    public void deleteOneStudentByStudentId(String studentId) {
        Student student = mongoTemplate.findById(studentId, Student.class);
        if (student != null) {
            mongoTemplate.remove(student);
        }
    }

    // 修改一位学生的信息
    public void updateOneStudent(Student student) {
        mongoTemplate.save(student);
    }

    // 根据主键id获取一名学生
    public void getOneStudentByStudentId(String studentId) {
        return mongoTemplate.findById(studentId, Student.class);
    }

    // 获取全部学生
    public List<Student> getAllStudent() {
        return mongoTemplate.findAll(Student.class);
    }

}
