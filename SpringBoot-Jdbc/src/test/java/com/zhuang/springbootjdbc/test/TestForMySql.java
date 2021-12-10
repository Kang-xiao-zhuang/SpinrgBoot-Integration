package com.zhuang.springbootjdbc.test;

import com.zhuang.springbootjdbc.dao.StudentDao;
import com.zhuang.springbootjdbc.entity.Edu;
import com.zhuang.springbootjdbc.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class TestForMySql {

    @Autowired
    private StudentDao studentDao;

    @Test
    void testAdd() {
        Student student = new Student();
        student.setId(6);
        student.setName("lisi");
        student.setAge(59);
        System.out.println(studentDao.addOne(student));
    }

    @Test
    void testDelete() {
        Student student = new Student();
        student.setId(2);
        Integer count = studentDao.deleteById(student.getId());
        System.out.println(count);
    }

    @Test
    void testUpdate() {
        Student student = new Student();
        student.setName("maniubi");
        student.setId(6);
        Integer count = studentDao.updateById(student);
        System.out.println(count);
    }

    @Test
    void testQuery() {
        Student student = studentDao.getOneById(6);
        System.out.println(student);
    }

    @Test
    void testQueryAll() {
        List<Student> studentList = studentDao.getAll();
        studentList.forEach(System.out::println);
    }
}
