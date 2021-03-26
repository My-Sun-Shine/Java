package com.springbootlearn;

import com.springbootlearn.bean.Person;
import com.springbootlearn.bean.Person01;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class SpringBootLearnApplicationTests {

    @Autowired
    Person person;
    @Autowired
    Person01 person01;

    @Autowired
    ApplicationContext ioc;

    @Test
    void contextLoads() {
    }

    /**
     * 测试ConfigurationProperties和@Value获取配置文件信息
     */
    @Test
    void TestPerson() {
        System.out.println(person.toString());
        System.out.println(person01.toString());
    }

    /**
     * 测试@ImportResource
     */
    @Test
    void TestImportResource(){
        System.out.println(ioc.containsBean("Test01"));
    }

    /**
     * 测试@Configuration和@Bean
     */
    @Test
    void TestConfigurationBean(){
        System.out.println(ioc.containsBean("test02"));
    }
}
