package spring.mybatis.dao;

import spring.mybatis.beans.Student;

import java.util.List;

/**
 * @Classname StudentDao
 * @Date 2020/4/6 16:47
 * @Created by Falling Stars
 * @Description
 */
public interface StudentDao {
    int insertStudent(Student student);
    List<Student> selectAllStudents();
}
