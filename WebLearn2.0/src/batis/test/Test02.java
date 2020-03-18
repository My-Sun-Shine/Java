package batis.test;

import batis.domain.Student;
import batis.service.StudentService;
import batis.service.impl.StudentServiceImpl;
import batis.util.ServiceFactory;

/**
 * @Classname Test02
 * @Date 2020/3/17 23:32
 * @Created by Falling Stars
 * @Description MyBaits框架下的测试Student的增删改查
 */
public class Test02 {
    public static void main(String[] args) {
        StudentServiceImpl serviceImpl = new StudentServiceImpl();
        StudentService service = (StudentService) ServiceFactory.getService(serviceImpl);
        service.insert(new Student("AA", "aA", 10));
    }
}
