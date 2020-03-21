package com.crm.settings.service.impl;

import com.crm.settings.dao.DeptDao;
import com.crm.settings.domain.Dept;
import com.crm.settings.service.DeptService;
import com.crm.utils.SqlSessionUtil;

import java.util.List;

/**
 * @Classname DeptServiceImpl
 * @Date 2020/3/21 21:26
 * @Created by Falling Stars
 * @Description 部门业务层实现
 */
public class DeptServiceImpl implements DeptService {
    private DeptDao deptDao = SqlSessionUtil.getSqlSession().getMapper(DeptDao.class);

    @Override
    public boolean saveDept(Dept dept) {
        return deptDao.saveDept(dept) == 1;
    }

    @Override
    public List<Dept> getDeptList() {
        return deptDao.getDeptList();
    }
}
