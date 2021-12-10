package com.zhuang.springbootmybatis.service;

import com.zhuang.springbootmybatis.entity.Student;

import java.util.List;

/**
 * @author Zhuang
 * @version 1.0
 * @description: StudentService接口
 * @date 2021/12/9 15:10
 */
public interface StudentService {
    /**
     * 根据主键查找学生
     *
     * @param id 主键
     * @return student实体
     */
    public Student findOne(Integer id);

    /**
     * 查找所有学生实体
     *
     * @return List集合
     */
    public List<Student> findAll();

    /**
     * 添加一个学生实体
     *
     * @param student student实体
     * @return 影响个数
     */
    public Integer addOne(Student student);

    /**
     * 根据主键删除学生
     *
     * @param id 主键值
     * @return 影响个数
     */
    public Integer deleteById(Integer id);

    /**
     * 更新学生数据
     *
     * @param student student实体
     * @return 影响个数
     */
    public Integer updateById(Student student);
}
