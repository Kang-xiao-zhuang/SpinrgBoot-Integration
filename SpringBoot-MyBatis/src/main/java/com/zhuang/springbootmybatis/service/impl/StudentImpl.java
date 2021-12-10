package com.zhuang.springbootmybatis.service.impl;

import com.zhuang.springbootmybatis.entity.Student;
import com.zhuang.springbootmybatis.mapper.StudentMapper;
import com.zhuang.springbootmybatis.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Zhuang
 * @version 1.0
 * @description: StudentImpl实现类
 * @date 2021/12/9 15:11
 */

@Repository
@Transactional(rollbackFor = Exception.class)
public class StudentImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Student findOne(Integer id) {
        return studentMapper.findOne(id);
    }

    @Override
    public List<Student> findAll() {
        return studentMapper.findAll();
    }

    @Override
    public Integer addOne(Student student) {
        return studentMapper.addOne(student);
    }

    @Override
    public Integer deleteById(Integer id) {
        return studentMapper.deleteById(id);
    }

    @Override
    public Integer updateById(Student student) {
        return studentMapper.updateById(student);
    }
}
