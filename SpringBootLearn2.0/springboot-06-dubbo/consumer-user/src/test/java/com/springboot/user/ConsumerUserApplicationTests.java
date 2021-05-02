package com.springboot.user;

import com.springboot.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ConsumerUserApplicationTests {

    @Autowired
    UserService userService;

    @Test
    public void contextLoads() {
        userService.hello();
    }

}
