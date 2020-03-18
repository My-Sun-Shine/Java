package batis02.dao;

import batis02.domain.Student;

import java.util.List;

/**
 * @Classname TStudentDao
 * @Date 2020/3/17 23:17
 * @Created by Falling Stars
 * @Description MyBaits框架下的Student项目的数据交互层接口
 */

/**
 *  将Mapper.xml中statement的id和StudentDao.java接口的方法名保持一致
 *  将Mapper.xml中statement的parameterType和StudentDao.java接口的方法输入参数类型保持一致
 *  将Mapper.xml中statement的resultType和StudentDao.java接口的方法输出结果类型保持一致
 */
public interface StudentDao {

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
