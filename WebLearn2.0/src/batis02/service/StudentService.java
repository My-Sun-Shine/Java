package batis02.service;

import batis02.domain.Student;

import java.util.List;

/**
 * @Classname StudentService
 * @Date 2020/3/17 23:27
 * @Created by Falling Stars
 * @Description MyBaits框架下的Student项目的业务层接口
 */
public interface StudentService {
    /**
     * 新增
     *
     * @param student
     */
    void insert(Student student);

    /**
     * 更新
     *
     * @param student
     */
    void update(Student student);

    /**
     * 删除
     *
     * @param id
     */
    void delete(String id);

    /**
     * 查询一条
     *
     * @param id
     * @return
     */
    Student getById(String id);

    /**
     * 查询全部
     *
     * @return
     */
    List<Student> getList();
}
