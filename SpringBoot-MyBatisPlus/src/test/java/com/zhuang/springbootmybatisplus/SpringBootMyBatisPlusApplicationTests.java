package com.zhuang.springbootmybatisplus;

import com.zhuang.springbootmybatisplus.entity.Student;
import com.zhuang.springbootmybatisplus.mapper.StudentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootMyBatisPlusApplicationTests {

    @Autowired
    private StudentMapper studentMapper;


    @Test
    void contextLoads() {
    }

    @Test
    void AddOne() {
        Student student = new Student();
        student.setId(199);
        student.setName("zhuang123456");
        student.setAge(18);
        studentMapper.insert(student);
    }
}
