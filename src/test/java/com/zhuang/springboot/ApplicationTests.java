package com.zhuang.springboot;

import com.zhuang.springbootmybatisplus.entity.Student;
import com.zhuang.springbootmybatisplus.mapper.StudentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private StudentMapper studentMapper;

    @Test
    void contextLoads() {
    }

    @Test
    public void findOne() {
        Student student = new Student();
        student.setId(9);
        student.setName("zhuang");
        student.setAge(18);
        studentMapper.insert(student);
    }
}
