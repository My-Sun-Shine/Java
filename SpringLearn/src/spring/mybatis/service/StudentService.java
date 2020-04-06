package spring.mybatis.service;

import spring.mybatis.beans.Student;

import java.util.List;

/**
 * @Classname StudentService
 * @Date 2020/4/6 17:10
 * @Created by Falling Stars
 * @Description
 */
public interface StudentService {
    public int addStudent(Student student);

    public List<Student> queryStudents();
}
