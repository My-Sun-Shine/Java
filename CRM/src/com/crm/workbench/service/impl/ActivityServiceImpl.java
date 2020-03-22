package com.crm.workbench.service.impl;


import com.crm.utils.SqlSessionUtil;
import com.crm.workbench.dao.ActivityDao;
import com.crm.workbench.domain.Activity;
import com.crm.workbench.service.ActivityService;

/**
 * @Classname ActivityServiceImpl
 * @Date 2020/3/22 16:14
 * @Created by Falling Stars
 * @Description 市场活动业务层实现
 */
public class ActivityServiceImpl implements ActivityService {
    ActivityDao activityDao = (ActivityDao) SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);

    @Override
    public boolean saveActivity(Activity activity) {
        return activityDao.saveActivity(activity) == 1;
    }
}
