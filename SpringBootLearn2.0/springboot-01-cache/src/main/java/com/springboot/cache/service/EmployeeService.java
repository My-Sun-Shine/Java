package com.springboot.cache.service;

import com.springboot.cache.bean.Employee;
import com.springboot.cache.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname EmployeeService
 * @Date 2021/4/24 16:02
 * @Created by FallingStars
 * @Description
 */
@Service
public class EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;

    public Employee getEmp(Integer id) {
        System.out.println("查询" + id + "号员工");
        Employee emp = employeeMapper.getEmpById(id);
        return emp;
    }

    public Employee updateEmp(Employee employee) {
        System.out.println("updateEmp:" + employee);
        employeeMapper.updateEmp(employee);
        return employee;
    }

    public void deleteEmp(Integer id) {
        System.out.println("deleteEmp:" + id);
        employeeMapper.deleteEmpById(id);
    }

    public Employee getEmpByLastName(String lastName) {
        return employeeMapper.getEmpByLastName(lastName);
    }
}
