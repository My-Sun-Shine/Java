package ssm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ssm.beans.Student;
import ssm.dao.StudentDao;
import ssm.service.StudentService;


@Service
public class StudentServiceImpl implements StudentService {

    //引用类型
    //byType
    @Autowired
    private StudentDao stuDao;


    @Override
    public List<Student> queryStudents() {
        List<Student> students = stuDao.selectStudents();
        return students;

    }

}
