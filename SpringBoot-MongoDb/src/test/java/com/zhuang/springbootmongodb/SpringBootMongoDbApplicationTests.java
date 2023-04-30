package com.zhuang.springbootmongodb;

import com.mongodb.client.MongoIterable;
import com.zhuang.springbootmongodb.dao.StudentDao;
import com.zhuang.springbootmongodb.entity.Student;
import com.zhuang.springbootmongodb.util.MongoDBUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class SpringBootMongoDbApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private StudentDao studentDao;

    @Test
    void addOneStudent() {
        //插入10行
        for (int count = 0; count < 10; count++) {
            Student student = new Student()
                    .setStudentId("student_" + count) //如果自己不去设置id则系统会分配给一个id
                    .setStudentName("Godfery" + count)
                    .setStudentAge(count)
                    .setStudentScore(98.5 - count)
                    .setStudentBirthday(new Date());
            studentDao.save(student);
        }
    }

    @Test
    void deleteOneStudentByStudentId() {
        //   删除id为student_0的学生
        studentDao.deleteById("student_0");
    }

    @Test
    void updateOneStudent() {
        // 修改姓名为Godfery1的Student年龄为22
        Student student = studentDao.getAllByStudentName("Godfery1");
        student.setStudentAge(22);
        studentDao.save(student);
    }

    @Test
    void getOneStudentByStudentId() {
        System.out.println(studentDao.findById("student_1"));
    }

    @Test
    void getAllStudent() {
        List<Student> studentList = studentDao.findAll();
        studentList.forEach(System.out::println);
    }

}
