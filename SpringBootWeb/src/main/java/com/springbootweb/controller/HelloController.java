package com.springbootweb.controller;

import com.springbootweb.entity.Department;
import com.springbootweb.entity.Employee;
import com.springbootweb.entity.User;
import com.springbootweb.exception.UserNotExistException;
import com.springbootweb.mapper.DepartmentMapper;
import com.springbootweb.mapper.EmployeeMapper;
import com.springbootweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Classname HelloController
 * @Date 2021/4/10 22:37
 * @Created by FallingStars
 * @Description
 */
@Controller
@RequestMapping("/hello")
public class HelloController {
    @ResponseBody
    @RequestMapping("/m1")
    public String m1(@RequestParam("user") String user) {
        if (user.equals("admin")) {
            throw new UserNotExistException();
        }
        return "Hello World";
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @ResponseBody
    @GetMapping("/test01")
    public Map<String, Object> test01() {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * FROM department");
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }

    }

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    EmployeeMapper employeeMapper;

    @ResponseBody
    @GetMapping("/dept/{id}")
    public Department getDepartment(@PathVariable("id") Integer id) {
        return departmentMapper.getDeptById(id);
    }

    @ResponseBody
    @GetMapping("/dept")
    public Department insertDept(Department department) {
        departmentMapper.insertDept(department);
        return department;
    }

    @ResponseBody
    @GetMapping("/emp/{id}")
    public Employee getEmp(@PathVariable("id") Integer id) {
        return employeeMapper.getEmpById(id);
    }

    @Autowired
    UserRepository userRepository;

    @ResponseBody
    @GetMapping("/user/{id}")
    public User getUser(@PathVariable("id") Integer id){
        User user = userRepository.findById(id).get();
        return user;
    }

    @ResponseBody
    @GetMapping("/user")
    public User insertUser(User user){
        User save = userRepository.save(user);
        return save;
    }

}
