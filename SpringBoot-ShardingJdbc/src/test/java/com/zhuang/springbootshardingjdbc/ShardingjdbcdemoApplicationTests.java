package com.zhuang.springbootshardingjdbc;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhuang.springbootshardingjdbc.entity.Course;
import com.zhuang.springbootshardingjdbc.entity.Udict;
import com.zhuang.springbootshardingjdbc.entity.User;
import com.zhuang.springbootshardingjdbc.mapper.CourseMapper;
import com.zhuang.springbootshardingjdbc.mapper.UdictMapper;
import com.zhuang.springbootshardingjdbc.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ShardingjdbcdemoApplicationTests {

    //注入mapper
    @Autowired
    private CourseMapper courseMapper;

    //注入user的mapper
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UdictMapper udictMapper;

    //======================测试公共表===================
    //添加操作
    @Test
    public void addDict() {
        Udict udict = new Udict();
        udict.setUstatus("a");
        udict.setUvalue("已启用");
        udictMapper.insert(udict);
    }

    //删除操作
    @Test
    public void deleteDict() {
        QueryWrapper<Udict> wrapper = new QueryWrapper<>();
        //设置userid值
        wrapper.eq("dictid", 465191484111454209L);
        udictMapper.delete(wrapper);
    }

    //======================测试垂直分库==================
    //添加操作
    @Test
    public void addUserDb() {
        User user = new User();
        user.setUsername("lucymary");
        user.setUstatus("a");
        userMapper.insert(user);
    }

    //查询操作
    @Test
    public void findUserDb() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //设置userid值
        wrapper.eq("user_id", 901201511999078401L);
        User user = userMapper.selectOne(wrapper);
        System.out.println(user);
    }


    //======================测试水平分库=====================
    //添加操作
    @Test
    public void addCourseDb() {
        for (int i = 50; i > 0; i--) {
            Course course = new Course();
            course.setCname("javademo1");
            //分库根据user_id
            course.setUserId(111L);
            course.setCstatus("Normal1");
            courseMapper.insert(course);
        }
    }

    //查询操作
    @Test
    public void findCourseDb() {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        //设置userid值
        wrapper.eq("user_id", 111L);
        //设置cid值
        wrapper.eq("cid", 465162909769531393L);
        Course course = courseMapper.selectOne(wrapper);
        System.out.println(course);
    }

    //=======================测试水平分表===================
    //添加课程的方法
    @Test
    public void addCourse() {
        for (int i = 1; i <= 10; i++) {
            Course course = new Course();
            course.setCname("java" + i);
            course.setUserId(100L);
            course.setCstatus("Normal" + i);
            courseMapper.insert(course);
        }
    }

    //查询课程的方法
    @Test
    public void findCourse() {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("cid", 465114666322886656L);
        Course course = courseMapper.selectOne(wrapper);
        System.out.println(course);
    }

}
