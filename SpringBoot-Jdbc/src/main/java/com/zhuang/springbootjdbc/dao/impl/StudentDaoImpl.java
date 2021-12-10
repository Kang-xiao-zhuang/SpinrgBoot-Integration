package com.zhuang.springbootjdbc.dao.impl;

import com.zhuang.springbootjdbc.dao.StudentDao;
import com.zhuang.springbootjdbc.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @Classname StudentDaoImpl
 * @Description StudentDaoImpl服务层
 * @Date 2021/12/09 17:25
 * @Author by Zhuang
 */
@Repository
public class StudentDaoImpl implements StudentDao {

    @Autowired
    JdbcTemplate jdbcTemplate;


    @Override
    public Integer addOne(Student student) {
        String sql = "INSERT INTO student (id, name, age)VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, student.getId().toString());
                ps.setString(2, student.getName());
                ps.setString(3, student.getAge().toString());
            }
        });
    }

    @Override
    public Integer deleteById(Integer id) {
        String sql = "DELETE FROM student WHERE id=?";
        return jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, id);
            }
        });
    }

    @Override
    public Integer updateById(Student student) {
        String sql = "UPDATE student s SET s.name=? WHERE s.id=?";
        return jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, student.getName());
                ps.setInt(2, student.getId());
            }
        });
    }

    @Override
    public Student getOneById(Integer id) {
        String sql = "SELECT * FROM student s WHERE id=?";
        Student student = jdbcTemplate.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Student.class));
        return student;
    }

    @Override
    public List<Student> getAll() {
        String sql = "SELECT * FROM student s";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Student.class));
    }
}
