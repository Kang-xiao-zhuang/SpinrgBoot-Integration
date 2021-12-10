package com.zhuang.springbootmybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhuang.springbootmybatisplus.entity.Student;
import com.zhuang.springbootmybatisplus.mapper.StudentMapper;
import com.zhuang.springbootmybatisplus.service.StudentService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author KangXiaoZhuang
 * @since 2021-12-09
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

}
