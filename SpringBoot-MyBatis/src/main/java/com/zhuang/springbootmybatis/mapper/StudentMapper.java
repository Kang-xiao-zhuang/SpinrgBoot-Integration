package com.zhuang.springbootmybatis.mapper;

import com.zhuang.springbootmybatis.entity.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Zhuang
 */
@Mapper
public interface StudentMapper {
    /**
     * 根据主键查找学生
     *
     * @param id 主键
     * @return student实体
     */
    Student findOne(Integer id);

    /**
     * 查找所有学生实体
     *
     * @return List集合
     */
    List<Student> findAll();

    /**
     * 添加一个学生实体
     *
     * @param student student实体
     * @return 影响个数
     */
    Integer addOne(Student student);

    /**
     * 根据主键删除学生
     *
     * @param id 主键值
     * @return 影响个数
     */
    Integer deleteById(Integer id);

    /**
     * 更新学生数据
     *
     * @param student student实体
     * @return 影响个数
     */
    Integer updateById(Student student);
}
