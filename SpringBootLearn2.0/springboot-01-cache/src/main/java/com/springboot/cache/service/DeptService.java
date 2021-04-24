package com.springboot.cache.service;

import com.springboot.cache.bean.Department;
import com.springboot.cache.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname DeptService
 * @Date 2021/4/24 16:04
 * @Created by FallingStars
 * @Description
 */
@Service
public class DeptService {
    @Autowired
    DepartmentMapper departmentMapper;

    public Department getDeptById(Integer id) {
        System.out.println("查询部门" + id);
        Department department = departmentMapper.getDeptById(id);
        return department;
    }
}
