package batis.test;

import batis.domain.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @Classname Test01
 * @Date 2020/3/17 20:20
 * @Created by Falling Stars
 * @Description MyBatis框架，测试程序，添加CRUD操作
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


        //getById(sqlSessionFactory);
        //getList(sqlSessionFactory);
        //insert(sqlSessionFactory);
        //update(sqlSessionFactory);
        //delete(sqlSessionFactory);

    }

    /**
     * 查询一条
     *
     * @param sqlSessionFactory
     */
    private static void getById(SqlSessionFactory sqlSessionFactory) {
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
     *
     * @param sqlSessionFactory
     */
    private static void getList(SqlSessionFactory sqlSessionFactory) {
        SqlSession session = sqlSessionFactory.openSession();
        List<Student> list = session.selectList("batis.student.getList");
        System.out.println("getList，输出结果：");
        System.out.println(list);

        session.close();
    }

    /**
     * 插入一条
     *
     * @param sqlSessionFactory
     */
    private static void insert(SqlSessionFactory sqlSessionFactory) {
        SqlSession session = sqlSessionFactory.openSession();
        Student newStudent = new Student();
        newStudent.setId("A0000");
        newStudent.setName("爷爷");
        newStudent.setAge(66);
        session.insert("batis.student.insert", newStudent);
        //mybatis是手动提交事务
        session.commit();
        session.close();
    }

    /**
     * 更新一条
     *
     * @param sqlSessionFactory
     */
    private static void update(SqlSessionFactory sqlSessionFactory) {
        SqlSession session = sqlSessionFactory.openSession();
        Student updateStu = new Student("A0000", "老爷爷", 100);
        session.update("batis.student.update", updateStu);

        session.commit();
        session.close();
    }


    /**
     * 删除一条
     *
     * @param sqlSessionFactory
     */
    private static void delete(SqlSessionFactory sqlSessionFactory) {
        SqlSession session = sqlSessionFactory.openSession();

        session.delete("batis.student.delete", "A0000");

        session.commit();
        session.close();
    }
}
