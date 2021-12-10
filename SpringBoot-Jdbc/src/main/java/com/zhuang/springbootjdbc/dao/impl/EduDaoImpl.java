package com.zhuang.springbootjdbc.dao.impl;

import com.zhuang.springbootjdbc.dao.EduDao;
import com.zhuang.springbootjdbc.entity.Edu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @Classname EduDaoImpl
 * @Description EduDaoImpl服务层
 * @Date 2021/12/09 17:19
 * @Author by Zhuang
 */
@Repository
public class EduDaoImpl implements EduDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Integer addOne(Edu edu) {
        String sql = "INSERT INTO FABADM.EDU ( NAME, LASTEVENTNAME) VALUES (?,?)";
        return jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, edu.getName());
                ps.setString(2, edu.getLasteventname());
            }
        });
    }

    @Override
    public Integer deleteById(String name) {
        String sql = "DELETE FROM FABADM.EDU WHERE NAME=?";
        return jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, name);
            }
        });
    }

    @Override
    public Integer updateById(Edu edu) {
        String sql = "UPDATE FABADM.EDU SET NAME=? WHERE LASTEVENTCOMMENT=?";
        return jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, edu.getName());
                ps.setString(2, edu.getLasteventcomment());
            }
        });
    }

    @Override
    public Edu getOneById(String name) {
        String sql = "SELECT * FROM EDU WHERE NAME=?";
        Edu edu = jdbcTemplate.queryForObject(sql, new Object[]{name}, new BeanPropertyRowMapper<>(Edu.class));
        return edu;
    }

    @Override
    public List<Edu> getAll() {
        String sql = "SELECT * FROM EDU WHERE ROWNUM<5";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Edu.class));
    }
}
