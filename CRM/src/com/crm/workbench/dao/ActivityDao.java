package com.crm.workbench.dao;

import com.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

/**
 * @Classname ActivityDao
 * @Date 2020/3/22 16:13
 * @Created by Falling Stars
 * @Description 市场活动数据库交互
 */
public interface ActivityDao {
    int saveActivity(Activity activity);

    int getTotalByCondition(Map<String, Object> map);

    List<Activity> getListByCondition(Map<String, Object> map);

    int deleteByAId(String[] ids);
}
