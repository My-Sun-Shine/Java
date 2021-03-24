package com.springbootlearn;

import com.springbootlearn.bean.Person;
import com.springbootlearn.bean.Person01;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootLearnApplicationTests {

    @Autowired
    Person person;
    @Autowired
    Person01 person01;

    @Test
    void contextLoads() {
    }

    @Test
    void TestPerson() {
        System.out.println(person.toString());
        System.out.println(person01.toString());
    }
}
