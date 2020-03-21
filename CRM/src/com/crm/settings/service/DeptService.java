package com.crm.settings.service;

import com.crm.settings.domain.Dept;

import java.util.List;

/**
 * @Classname DeptService
 * @Date 2020/3/21 21:25
 * @Created by Falling Stars
 * @Description 部门业务层
 */
public interface DeptService {
    boolean saveDept(Dept dept);

    List<Dept> getDeptList();
}
