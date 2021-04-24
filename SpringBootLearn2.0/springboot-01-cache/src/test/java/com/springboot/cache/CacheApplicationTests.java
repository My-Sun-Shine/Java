package com.springboot.cache;

import com.alibaba.fastjson.JSONObject;
import com.springboot.cache.bean.Employee;
import com.springboot.cache.mapper.EmployeeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class CacheApplicationTests {

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RedisTemplate<Object, Employee> employeeRedisTemplate;

    @Autowired
    RedisTemplate newRedisTemplate;

    /**
     * Redis常见的五大数据类型
     * String（字符串）、List（列表）、Set（集合）、Hash（散列）、ZSet（有序集合）
     * stringRedisTemplate.opsForValue()[String（字符串）]
     * stringRedisTemplate.opsForList()[List（列表）]
     * stringRedisTemplate.opsForSet()[Set（集合）]
     * stringRedisTemplate.opsForHash()[Hash（散列）]
     * stringRedisTemplate.opsForZSet()[ZSet（有序集合）]
     */
    @Test
    void test01() {
        stringRedisTemplate.opsForValue().append("msg", "hello");
        String msg = stringRedisTemplate.opsForValue().get("msg");
        System.out.println(msg);

        stringRedisTemplate.opsForList().leftPush("myList", "1");
        stringRedisTemplate.opsForList().leftPush("myList", "2");
    }

    @Test
    public void test02() {
        Employee employee = employeeMapper.getEmpById(1);
        //默认如果保存对象，使用jdk序列化机制，序列化后的数据保存到redis中
        redisTemplate.opsForValue().set("employee-01", employee);

        //将数据以json的方式保存
        //redisTemplate默认的序列化规则；改变默认的序列化规则；新建MyRedisConfig
        employeeRedisTemplate.opsForValue().set("employee-03", employee);
        newRedisTemplate.opsForValue().set("employee-04", employee);
    }

    @Test
    void contextLoads() {
    }

}
