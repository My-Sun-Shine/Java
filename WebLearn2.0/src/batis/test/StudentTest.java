package batis.test;

/**
 * @Classname StudentTest
 * @Date 2020/3/17 22:10
 * @Created by Falling Stars
 * @Description JUnit测试
 */

import batis.domain.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * JUnit：企业测试工具，主要测试业务层对业务的处理
 * <p>
 * 需要导入JUnit包
 * 命名规范：类名+Test
 *
 * 使用注解 @Test 引入JUnit4
 */
public class StudentTest {

    /**
     * 查询一条
     */
    @Test
    public void test_getById() {
        System.out.println("test_getById--------------------------------------------------------");
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);


        //使用工厂创建SqlSession对象
        SqlSession session = sqlSessionFactory.openSession();

        /*
         * 参数1：根据 [命名空间.id] 格式，获取需要的sql语句
         * 参数2：为sql语句传递的参数
         * */
        Student student = session.selectOne("batis.student.getById", "A0001");
        System.out.println("getById，输出结果：");
        System.out.println(student);

        session.close();
    }

    /**
     * 查询多条
     */
    @Test
    public void test_getList() {
        System.out.println("test_getList--------------------------------------------------------");

        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession session = sqlSessionFactory.openSession();
        List<Student> list = session.selectList("batis.student.getList");
        System.out.println("getList，输出结果：");
        System.out.println(list);

        session.close();
    }


}
