package com.springbootweb.mapper;

import com.springbootweb.entity.Employee;

/**
 * @Classname EmployeeMapper
 * @Date 2021/4/14 22:06
 * @Created by FallingStars
 * @Description @Mapper或者@MapperScan将接口扫描装配到容器中
 */
public interface EmployeeMapper {
    public Employee getEmpById(Integer id);

    public void insertEmp(Employee employee);
}
