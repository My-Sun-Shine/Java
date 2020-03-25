package com.crm.workbench.dao;

import com.crm.workbench.domain.ActivityRemark;

import java.util.List;

/**
 * @Classname ActivityRemarkDao
 * @Date 2020/3/22 22:29
 * @Created by Falling Stars
 * @Description
 */
public interface ActivityRemarkDao {
    int deleteById(String[] ids);

    int selectById(String[] ids);

    List<ActivityRemark> getRemarkListById(String id);

    int deleteRemarkById(String id);
}
