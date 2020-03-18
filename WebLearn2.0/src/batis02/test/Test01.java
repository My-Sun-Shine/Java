package batis02.test;

import batis02.domain.Student;
import batis02.service.StudentService;
import batis02.service.impl.StudentServiceImpl;
import batis02.util.ServiceFactory;

/**
 * @Classname Test01
 * @Date 2020/3/18 11:03
 * @Created by Falling Stars
 * @Description 测试程序
 */
public class Test01 {
    public static void main(String[] args) {
        StudentService service = (StudentService) ServiceFactory.getService(new StudentServiceImpl());
        Student student = service.getById("A0001");
        System.out.println(student);

    }
}
