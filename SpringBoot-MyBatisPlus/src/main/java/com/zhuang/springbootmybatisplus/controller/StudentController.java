package com.zhuang.springbootmybatisplus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhuang.springbootmybatisplus.entity.Student;
import com.zhuang.springbootmybatisplus.mapper.StudentMapper;
import com.zhuang.springbootmybatisplus.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author KangXiaoZhuang
 * @since 2021-12-09
 */
@RestController
public class StudentController {


    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentMapper studentMapper;

    /**
     * 根据id查询一个学生
     *
     * @param id id
     * @return 学生实体
     */
    @GetMapping("/findOne/{id}")
    public Student findOne(@PathVariable Integer id) {
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("id", id);
        return studentService.getOne(studentQueryWrapper);
    }

    /**
     * 增加一个学生
     *
     * @param student 学生实体
     * @return 是否成功
     */
    @PostMapping("/addStudent")
    public boolean addOne(Student student) {
        return studentService.save(student);
    }

    /**
     * 根据id删除一个学生
     *
     * @param id id
     * @return 是否成功
     */
    @DeleteMapping("/deleteStudent/{id}")
    public boolean deleteOne(@PathVariable Integer id) {
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("id", id);
        return studentService.remove(studentQueryWrapper);
    }

    /**
     * 更新一个学生
     *
     * @param student 学生实体
     * @return 是否成功
     */
    @PutMapping("/updateStudent")
    public boolean updateOne(Student student) {
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("id", student.getId());
        Student newStudent = studentService.getOne(studentQueryWrapper);
        newStudent.setName(student.getName());
        newStudent.setAge(student.getAge());
        return studentService.update(newStudent, studentQueryWrapper);
    }

    /**
     * 获取所有学生信息
     *
     * @return List集合
     */
    @GetMapping("/getAllStudent")
    public List<Student> test() {
        return studentMapper.selectList(null);
    }
}

