package batis.dao.impl;

import batis.dao.StudentDao;
import batis.domain.Student;
import batis.util.DBUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @Classname TStudentDaoImpl
 * @Date 2020/3/17 23:20
 * @Created by Falling Stars
 * @Description MyBaits框架下的Student项目的数据交互层接口实现类
 */
public class StudentDaoImpl implements StudentDao {
    /**
     * 新增
     *
     * @param student
     */
    @Override
    public void insert(Student student) {
        SqlSession session = DBUtil.getSession();
        session.insert("batis.student.insert", student);
    }

    /**
     * 更新
     *
     * @param student
     */
    @Override
    public void update(Student student) {
        SqlSession session = DBUtil.getSession();
        session.update("batis.student.update", student);
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public void delete(String id) {
        SqlSession session = DBUtil.getSession();
        session.delete("batis.student.delete", id);
    }

    /**
     * 查询一条
     *
     * @param id
     * @return
     */
    @Override
    public Student getById(String id) {
        SqlSession session = DBUtil.getSession();
        Student student = session.selectOne("batis.student.getById", id);
        return student;
    }

    /**
     * 查询全部
     *
     * @return
     */
    @Override
    public List<Student> getList() {
        SqlSession session = DBUtil.getSession();
        List<Student> list = session.selectList("batis.student.getList");
        return list;
    }
}
