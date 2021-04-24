package com.springboot.cache.controller;

import com.springboot.cache.bean.Department;
import com.springboot.cache.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname DeptController
 * @Date 2021/4/24 16:10
 * @Created by FallingStars
 * @Description
 */
@RequestMapping("department")
@RestController
public class DeptController {
    @Autowired
    DeptService deptService;

    @GetMapping("/get/{id}")
    public Department getDept(@PathVariable("id") Integer id) {
        return deptService.getDeptById(id);
    }
}
