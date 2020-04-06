package service.impl;



import beans.Student;
import dao.StudentDao;
import service.StudentService;

import java.util.List;

/**
 * @Classname StudentServiceImpl
 * @Date 2020/4/6 17:11
 * @Created by Falling Stars
 * @Description
 */
public class StudentServiceImpl implements StudentService {

    private StudentDao studentDao;

    public void setStudentDao(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public int addStudent(Student student) {
        int rows  = studentDao.insertStudent(student);
        return rows;
    }

    @Override
    public List<Student> queryStudents() {
        List<Student> students  = studentDao.selectAllStudents();
        return students;
    }
}
