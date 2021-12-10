package com.zhuang.springbootjdbc.dao;
import com.zhuang.springbootjdbc.entity.Edu;
import java.util.List;

/**
 * @author Zhuang
 */
public interface EduDao {
    /**
     * 添加一条数据
     *
     * @param edu edu实体
     * @return 影响个数
     */
    Integer addOne(Edu edu);

    /**
     * 根据id删除一条数据
     *
     * @param name 指定主键名
     * @return 影响个数
     */
    Integer deleteById(String name);

    /**
     * 根据id更新一条数据
     *
     * @param edu edu实体
     * @return 影响个数
     */
    Integer updateById(Edu edu);

    /**
     * 根据id查询一条数据
     *
     * @param name 指定主键名
     * @return 影响个数
     */
    Edu getOneById(String name);

    /**
     * 查询所有的数据
     *
     * @return List集合
     */
    List<Edu> getAll();
}
