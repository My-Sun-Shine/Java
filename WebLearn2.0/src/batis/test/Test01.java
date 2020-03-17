package batis.test;

import batis.domain.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Classname Test01
 * @Date 2020/3/17 20:20
 * @Created by Falling Stars
 * @Description MyBatis框架，测试程序
 */
public class Test01 {
    public static void main(String[] args) {
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
        System.out.println("输出结果：");
        System.out.println(student);

    }
}
