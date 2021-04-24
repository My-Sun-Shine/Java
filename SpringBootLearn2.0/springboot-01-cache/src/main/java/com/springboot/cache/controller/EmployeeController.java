package com.springboot.cache.controller;

import com.springboot.cache.bean.Employee;
import com.springboot.cache.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname EmployeeController
 * @Date 2021/4/24 16:01
 * @Created by FallingStars
 * @Description
 */
@RequestMapping("/employee")
@RestController
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping("/get/{id}")
    public Employee getEmployee(@PathVariable("id") Integer id) {
        Employee employee = employeeService.getEmp(id);
        return employee;
    }

    @GetMapping("/update")
    public Employee update(Employee employee) {
        Employee emp = employeeService.updateEmp(employee);

        return emp;
    }

    @GetMapping("/delete")
    public String deleteEmp(Integer id) {
        employeeService.deleteEmp(id);
        return "success";
    }

    @GetMapping("/get/lastname/{lastName}")
    public Employee getEmpByLastName(@PathVariable("lastName") String lastName) {
        return employeeService.getEmpByLastName(lastName);
    }

}
