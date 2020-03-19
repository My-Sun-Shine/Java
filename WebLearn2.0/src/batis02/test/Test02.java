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

        //1.测试parameterType:测试以String 为参数类型，parameterType属性，可以省略掉
        Student student1 = studentDao.select1("A0001");
        System.out.println(student1);

        //2.测试parameterType:测试以int 为参数类型
        List<Student> studentList = studentDao.select2(10);
        for (Student s : studentList) {
            System.out.println(s);
        }

        //3.测试parameterType：测试以引用类型为参数
        Student student = new Student();
        student.setId("A0001");
        Student student2 = studentDao.select3(student);
        System.out.println(student2);

        //4.测试parameterType：测试以map类型为参数
        Map<String, Object> map = new HashMap<>();
        map.put("name123", "大娃");
        map.put("age1", 10);
        Student student3 = studentDao.select4(map);
        System.out.println(student3);

        //5.#{}和${}:测试以${}来接收基本数据类型
        Student student4 = studentDao.select5("A0002");
        System.out.println(student4);

        //6.#{}和${}:测试like模糊查询 方式1：使用${}的形式
        List<Student> studentList2 = studentDao.select6("娃");
        for (Student s : studentList2) {
            System.out.println(s);
        }

        //7.#{}和${}:测试like模糊查询 方式2：使用#{}的形式
        List<Student> studentList3 = studentDao.select7("%娃%");
        for (Student s : studentList3) {
            System.out.println(s);
        }

        //8.#{}和${}:测试like模糊查询 方式2：使用#{}的形式
        List<Student> studentList4 = studentDao.select8("娃");
        for (Student s : studentList4) {
            System.out.println(s);
        }

        //9.测试resultType：测试返回基本数据类型 查询总记录数
        int total = studentDao.select9();
        System.out.println("Total:" + total);

        //10.测试resultType：测试返回引用数据类型
        Student student5 = studentDao.select10("A0005");
        System.out.println(student5);

        //11.测试resultType：测试返回map
        List<Map<String, Object>> mapList = studentDao.select11();
        for (Map<String, Object> m : mapList) {
            Set<String> set = m.keySet();
            for (String key : set) {
                System.out.println("key:" + key);
                System.out.println("value:" + m.get(key));
            }
            System.out.println("-----------------------------------");
        }

        //12.测试当数据库表字段和类属性命名不一致的情况；方式1：起别名，主要针对个别字段不一致
        List<Student> studentList5 = studentDao.select12();
        for (Student s : studentList5) {
            System.out.println(s);
        }

        //13.测试当数据库表字段和类属性命名不一致的情况，方式2：设置resultMap，为了应对表字段与类属性都不相同的情况
        List<Student> studentList6 = studentDao.select13();
        for (Student s : studentList6) {
            System.out.println(s);
        }
    }
}
