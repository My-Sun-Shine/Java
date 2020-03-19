package batis02.dao;

import batis02.domain.Student;
import batis02.vo.StudentClassroomVo;

import java.util.List;
import java.util.Map;

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

    Student select1(String s);

    List<Student> select2(int i);

    Student select3(Student s);

    Student select4(Map<String, Object> map);

    Student select5(String s);

    List<Student> select6(String a);

    List<Student> select7(String a);

    List<Student> select8(String s);

    int select9();

    Student select10(String a);

    List<Map<String,Object>> select11();

    List<Student> select12();

    List<Student> select13();

    List<Student> select14(Student s);

    List<Student> select15(String[] strArr);

    List<Student> select16(String s);

    List<Map<String,Object>> select17();

    List<StudentClassroomVo> select18();

    List<StudentClassroomVo> select19(String z);
}
