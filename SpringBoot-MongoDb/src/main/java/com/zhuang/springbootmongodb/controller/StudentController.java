package com.zhuang.springbootmongodb.controller;

import com.zhuang.springbootmongodb.entity.Student;
import com.zhuang.springbootmongodb.service.impl.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * description: StudentController
 * date: 2023/4/11 15:09
 * author: Zhuang
 * version: 1.0
 */
@RestController
public class StudentController {

    @Autowired
    private StudentServiceImpl studentService;


    @PostMapping("/add/{student}")
    public void addStudent(@PathVariable Student student) {
        studentService.addOneStudent(student);
    }

    @DeleteMapping("/delete/{studentId}")
    public void deleteStudent(@PathVariable String studentId) {
        studentService.deleteOneStudentByStudentId(studentId);
    }

    @PutMapping("/update/{student}")
    public void updateStudent(@PathVariable Student student) {
        studentService.updateOneStudent(student);
    }

    @GetMapping("/getOne/{studentId}")
    public void getOneStudent(@PathVariable String studentId) {
        studentService.getOneStudentByStudentId(studentId);
    }

    @GetMapping("/getAll")
    public List<Student> getAllStudent() {
        return studentService.getAllStudent();
    }
}
