package com.zhuang.springbootjdbc.controller;

import com.zhuang.springbootjdbc.dao.StudentDao;
import com.zhuang.springbootjdbc.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Classname ControllerForMySql
 * @Description 测试Mysql的接口
 * @Date 2021/12/09 17:19
 * @Author by Zhuang
 */
@RestController
public class ControllerForMySql {
    /**
     * jdbcTemplate
     */
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private StudentDao studentDao;

    /**
     * 根据主键查找学生
     *
     * @param id 主键
     * @return 学生实体
     */
    @GetMapping("/findStudentById/{id}")
    public Student findById(@PathVariable Integer id) {
        return studentDao.getOneById(id);
    }

    /**
     * 添加一个学生
     *
     * @param student 学生实体
     */
    @PostMapping("/addStudent")
    public void addStudent(Student student) {
        studentDao.addOne(student);
    }

    /**
     * 根据主键删除学生
     *
     * @param id 主键
     */
    @DeleteMapping("/deleteStudent/{id}")
    public void deleteStudent(@PathVariable Integer id) {
        studentDao.deleteById(id);
    }

    /**
     * 查询所有学生
     *
     * @return List集合
     */
    @RequestMapping("/getStudent")
    public List<Student> getStudent() {
        List<Student> lists = jdbcTemplate.query("SELECT * FROM student s",
                new BeanPropertyRowMapper<>(Student.class));
        return lists;
    }

}
