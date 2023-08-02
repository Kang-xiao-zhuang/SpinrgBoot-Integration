package com.zhuang.springbootmybatis.controller;

import com.zhuang.springbootmybatis.entity.Student;
import com.zhuang.springbootmybatis.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Zhuang
 * @version 1.0
 * @description: StudentController
 * @date 2021/12/9 15:14
 */
//@RestController("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/findOne/{id}")
    public Student findOne(@PathVariable Integer id) {
        return studentService.findOne(id);
    }

    @GetMapping("/findAll")
    public List<Student> findAll() {
        return studentService.findAll();
    }

    @PostMapping("/addStudent")
    public Integer addStudent(Student student) {
        return studentService.addOne(student);
    }

    @DeleteMapping("/deleteStudent/{id}")
    public Integer deleteStudent(@PathVariable Integer id) {
        return studentService.deleteById(id);
    }

    @PutMapping("/updateStudent")
    public Integer updateStudent(Student student) {
        return studentService.updateById(student);
    }
}
