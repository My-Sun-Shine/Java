package ssm.dao;

import ssm.beans.Student;

import java.util.List;


public interface StudentDao {

    List<Student> selectStudents();
}
