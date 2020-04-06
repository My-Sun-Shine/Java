package spring.mybatis.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.mybatis.beans.Student;
import spring.mybatis.service.StudentService;

import java.util.List;

/**
 * @Classname MyTest
 * @Date 2020/4/6 20:35
 * @Created by Falling Stars
 * @Description
 */
public class MyTest {
    private String configLocation = "spring/mybatis/applicationContext.xml";

    /**
     * 在ClassPathXmlApplicationContext的构造方法中，读取文件，也就是按文件的内容依次创建对象
     * <p>
     * DataSource myDataSource = new com.alibaba.druid.pool.DruidDataSource();
     * myDataSource.setUrl("jdbc:mysql://localhost:3306/springdb");
     * myDataSource.setUsername("root");
     * myDataSource.setPassword("123456");
     * myDataSource.init();
     * <p>
     * SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
     * bean.setDataSource(myDataSource);
     * bean.setConfigLocation(new Resource("classpath:spring/mybatis/mybatis-config.xml"));
     * SqlSessionFactory factory = bean.getObject();
     * <p>
     * <p>
     * SqlSession session = factory.openSession();
     * for(接口:spring.mybatis.dao){
     * 接口类型的Dao对象  xxDao  = session.getMapper(接口);
     * 创建好的Dao对象放入到spring的容器中，
     * spring使用map存储对象 map.put(接口名的首字母小写, xxDao)
     * }
     */
    @Test
    public void testSpring() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(configLocation);
        //获取容器中对象的信息
        String names[] = ctx.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
    }

    @Test
    public void testAddStudent() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(configLocation);
        StudentService service = (StudentService) ctx.getBean("studentService");
        Student student = new Student();
        student.setName("詹三");
        student.setAge(12);
        int rows = service.addStudent(student);
        System.out.println(rows);
    }

    @Test
    public void testQueryStudent() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(configLocation);
        StudentService service = (StudentService) ctx.getBean("studentService");
        List<Student> students = service.queryStudents();
        for (Student s : students) {
            System.out.println(s);
        }
    }
}
