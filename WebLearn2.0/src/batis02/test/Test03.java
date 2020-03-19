package batis02.test;

import batis02.dao.StudentDao;
import batis02.domain.Student;
import batis02.util.DBUtil;
import batis02.vo.StudentClassroomVo;

import java.util.*;

/**
 * @Classname Test03
 * @Date 2020/3/19 13:21
 * @Created by Falling Stars
 * @Description 动态sql，sql片段，多表连接
 */
public class Test03 {
    public static void main(String[] args) {
        StudentDao studentDao = DBUtil.getSession().getMapper(StudentDao.class);

        //14.测试动态sql：where+if
        Student s = new Student();
        s.setName("娃");
        s.setId("01");
        List<Student> sList = studentDao.select14(s);
        for (Student s1 : sList) {
            System.out.println(s1);
        }

        //15.测试动态sql：测试foreach
        String strArr[] = {"A0001", "A0002", "A0003"};
        List<Student> sList1 = studentDao.select15(strArr);
        for (Student s1 : sList1) {
            System.out.println(s1);
        }

        //16.测试sql片段
        List<Student> sList2 = studentDao.select16("A0001");
        for (Student s1 : sList2) {
            System.out.println(s1);
        }

        //17.测试多表联查 需求：查询出学生姓名和班级名称
        List<Map<String, Object>> mapList = studentDao.select17();
        for (Map<String, Object> map : mapList) {
            Set<String> set = map.keySet();
            for (String key : set) {
                System.out.println("key：" + key);
                System.out.println("value：" + map.get(key));
            }
            System.out.println("-----------------------------");
        }

        //18.查询出学生和班级所有信息，加VO
        /*
        * pojo（domain）：实体类对象，与数据库表一一对应
        *
        * vo：展示值的对象
        *
        * dto：传值对象
        * */
        List<StudentClassroomVo>  voList =studentDao.select18();
        for (StudentClassroomVo vo:voList){
            System.out.println(vo);
        }


        //19.查询出带有字母z的学生和班级所有信息
        List<StudentClassroomVo>  voList1 =studentDao.select19("娃");
        for (StudentClassroomVo vo:voList1){
            System.out.println(vo);
        }

    }
}
