package com.zhuang.springbootjdbc.controller;

import com.zhuang.springbootjdbc.dao.StudentDao;
import com.zhuang.springbootjdbc.entity.Edu;
import com.zhuang.springbootjdbc.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ControllerForMySql {
    //TODO 注释添加详细
    /**
     * jdbcTemplate
     */
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private StudentDao studentDao;

    @GetMapping("/findStudentById/{id}")
    public Student findById(@PathVariable Integer id) {
        return studentDao.getOneById(id);
    }

    @PostMapping("/addStudent")
    public void addStudent(Student student) {
        studentDao.addOne(student);
    }

    @DeleteMapping("/deleteStudent/{id}")
    public void deleteStudent(@PathVariable Integer id) {
        studentDao.deleteById(id);
    }


    @RequestMapping("/getStudent")
    public List<Student> getStudent() {
        List<Student> lists = jdbcTemplate.query("SELECT * FROM student s",
                new BeanPropertyRowMapper<>(Student.class));
        return lists;
    }

}
