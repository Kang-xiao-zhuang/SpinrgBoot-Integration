package com.zhuang.springbootjdbc.dao;
import com.zhuang.springbootjdbc.entity.Student;
import java.util.List;

/**
 * @author Zhuang
 */
public interface StudentDao {
    /**
     * 添加一条数据
     *
     * @param student student实体
     * @return 影响个数
     */
    Integer addOne(Student student);

    /**
     * 根据id删除一条数据
     *
     * @param id 指定主键名
     * @return 影响个数
     */
    Integer deleteById(Integer id);

    /**
     * 根据id更新一条数据
     *
     * @param student student实体
     * @return 影响个数
     */
    Integer updateById(Student student);

    /**
     * 根据id查询一条数据
     *
     * @param id 指定主键名
     * @return 影响个数
     */
    Student getOneById(Integer id);

    /**
     * 查询所有的数据
     *
     * @return List集合
     */
    List<Student> getAll();
}
