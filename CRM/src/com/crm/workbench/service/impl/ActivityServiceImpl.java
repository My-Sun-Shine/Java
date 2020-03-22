package com.crm.workbench.service.impl;


import com.crm.utils.SqlSessionUtil;
import com.crm.vo.PaginationVO;
import com.crm.workbench.dao.ActivityDao;
import com.crm.workbench.domain.Activity;
import com.crm.workbench.service.ActivityService;

import java.util.List;
import java.util.Map;

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

    @Override
    public PaginationVO<Activity> pageList(Map<String, Object> map) {
        System.out.println(map.toString());
        int total = activityDao.getTotalByCondition(map);
        List<Activity> activityList = activityDao.getListByCondition(map);
        PaginationVO<Activity> paginationVO = new PaginationVO<>();
        paginationVO.setTotal(total);
        paginationVO.setDataList(activityList);
        return paginationVO;
    }
}
