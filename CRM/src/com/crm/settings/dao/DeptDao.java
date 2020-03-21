package com.crm.settings.dao;

import com.crm.settings.domain.Dept; /**
 * @Classname DeptDao
 * @Date 2020/3/21 21:25
 * @Created by Falling Stars
 * @Description 部门数据库交互
 */
public interface DeptDao {
    int saveDept(Dept dept);
}
