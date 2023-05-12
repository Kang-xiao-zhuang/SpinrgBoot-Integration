package com.zhuang.springbootjdbc.controller;

import com.zhuang.springbootjdbc.dao.EduDao;
import com.zhuang.springbootjdbc.entity.Edu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Classname ControllerForOracle
 * @Description 测试Oracle的接口
 * @Date 2021/12/09 17:19
 * @Author by Zhuang
 */
@RestController
public class ControllerForOracle {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private EduDao eduDao;

    @RequestMapping("/")
    public String hello() {
        return "Hello world!";
    }

    @GetMapping("/findById/{name}")
    public Edu findById(@PathVariable String name) {
        return eduDao.getOneById(name);
    }

    @PostMapping("/addEdu")
    public void addEdu(Edu edu) {
        eduDao.addOne(edu);
    }

    @DeleteMapping("/deleteEdu/{name}")
    public void deleteEdu(@PathVariable String name) {
        eduDao.deleteById(name);
    }

    @RequestMapping("/getEdu")
    public List<Edu> test() {
        return jdbcTemplate.query("SELECT * FROM EDU WHERE ROWNUM <6",
                new BeanPropertyRowMapper<>(Edu.class));
    }

}
