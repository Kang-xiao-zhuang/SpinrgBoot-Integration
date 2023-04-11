package com.zhuang.springbootmongodb.dao;

import com.zhuang.springbootmongodb.entity.Student;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

/**
 * description: StudentDao
 * date: 2023/4/11 14:48
 * author: Zhuang
 * version: 1.0
 */
public interface StudentDao extends MongoRepository<Student, String> {

    Student getAllByStudentName(String studentName);

}
