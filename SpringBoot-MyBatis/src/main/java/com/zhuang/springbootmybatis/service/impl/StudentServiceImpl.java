package com.zhuang.springbootmybatis.service.impl;

import com.zhuang.springbootmybatis.entity.Student;
import com.zhuang.springbootmybatis.mapper.StudentMapper;
import com.zhuang.springbootmybatis.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Classname StudentImpl
 * @Description StudentImpl服务层
 * @Date 2021/12/09 17:19
 * @Author by Zhuang
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public class StudentServiceImpl implements StudentService {

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
