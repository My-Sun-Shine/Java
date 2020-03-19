package batis02.test;

import batis02.domain.Student;
import batis02.dao.StudentDao;
import batis02.util.DBUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Classname Test01
 * @Date 2020/3/18 11:03
 * @Created by Falling Stars
 * @Description 测试程序
 */
public class Test02 {
    public static void main(String[] args) {
        StudentDao studentDao = DBUtil.getSession().getMapper(StudentDao.class);

        Student student1 = studentDao.select1("A0001");
        System.out.println(student1);

        List<Student> studentList = studentDao.select2(10);
        for (Student s : studentList) {
            System.out.println(s);
        }

        Student student = new Student();
        student.setId("A0001");
        Student student2 = studentDao.select3(student);
        System.out.println(student2);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name123", "大娃");
        map.put("age1", 10);
        Student student3 = studentDao.select4(map);
        System.out.println(student3);

        Student student4 = studentDao.select5("A0002");
        System.out.println(student4);

        List<Student> studentList2 = studentDao.select6("娃");
        for (Student s : studentList2) {
            System.out.println(s);
        }

        List<Student> studentList3 = studentDao.select7("%娃%");
        for (Student s : studentList3) {
            System.out.println(s);
        }

        List<Student> studentList4 = studentDao.select8("娃");
        for (Student s : studentList4) {
            System.out.println(s);
        }

        int total = studentDao.select9();
        System.out.println("Total:" + total);

        Student student5 = studentDao.select10("A0005");
        System.out.println(student5);


        List<Map<String, Object>> mapList = studentDao.select11();
        for (Map<String, Object> m : mapList) {
            Set<String> set = m.keySet();
            for (String key : set) {
                System.out.println("key:" + key);
                System.out.println("value:" + m.get(key));
            }
            System.out.println("-----------------------------------");
        }

        List<Student> studentList5 =studentDao.select12();
        for (Student s : studentList5) {
            System.out.println(s);
        }


        List<Student> studentList6 =studentDao.select13();
        for (Student s : studentList6) {
            System.out.println(s);
        }
    }
}
