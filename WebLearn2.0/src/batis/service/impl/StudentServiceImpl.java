package batis.service.impl;

import batis.dao.StudentDao;
import batis.dao.impl.StudentDaoImpl;
import batis.domain.Student;
import batis.service.StudentService;

import java.util.List;

/**
 * @Classname StudentServiceImpl
 * @Date 2020/3/17 23:28
 * @Created by Falling Stars
 * @Description MyBaits框架下的Student项目的业务层接口实现类
 */
public class StudentServiceImpl implements StudentService {

    private StudentDao studentDao = new StudentDaoImpl();

    /**
     * 新增
     *
     * @param student
     */
    @Override
    public void insert(Student student) {
        studentDao.insert(student);
    }

    /**
     * 更新
     *
     * @param student
     */
    @Override
    public void update(Student student) {
        studentDao.update(student);
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(String id) {
        studentDao.delete(id);
    }

    /**
     * 查询一条
     *
     * @param id
     * @return
     */
    @Override
    public Student getById(String id) {
        return studentDao.getById(id);
    }

    /**
     * 查询全部
     *
     * @return
     */
    @Override
    public List<Student> getList() {
        return studentDao.getList();
    }
}
