package com.crm.workbench.service.impl;


import com.crm.settings.dao.UserDao;
import com.crm.settings.domain.User;
import com.crm.utils.SqlSessionUtil;
import com.crm.vo.PaginationVO;
import com.crm.workbench.dao.ActivityDao;
import com.crm.workbench.dao.ActivityRemarkDao;
import com.crm.workbench.domain.Activity;
import com.crm.workbench.domain.ActivityRemark;
import com.crm.workbench.service.ActivityService;

import java.util.HashMap;
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
    ActivityRemarkDao activityRemarkDao = (ActivityRemarkDao) SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
    UserDao userDao = (UserDao) SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

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

    @Override
    public boolean deleteActivity(String[] ids) {
        boolean flag = true;

        //查询出该市场活动数组对应的所有备注的数量
        int total = activityRemarkDao.selectById(ids);

        //先删除市场活动备注
        int count = activityRemarkDao.deleteById(ids);

        if (total != count) {
            flag = false;
        }

        //删除市场活动
        int count2 = activityDao.deleteByAId(ids);

        if (count2 != ids.length) {
            flag = false;
        }
        return flag;
    }

    @Override
    public Map<String, Object> showEditActivity(String id) {
        Activity activity = activityDao.getById(id);
        List<User> userList = userDao.getUserList();
        Map<String, Object> map = new HashMap<>();
        map.put("userList", userList);
        map.put("activity", activity);
        return map;
    }

    @Override
    public boolean updateActivity(Activity activity) {
        return activityDao.updateActivity(activity) == 1;
    }

    @Override
    public Activity detailActivity(String id) {
        Activity activity = activityDao.getDetailById(id);
        return activity;
    }

    @Override
    public List<ActivityRemark> getRemarkListById(String id) {
        return activityRemarkDao.getRemarkListById(id);
    }

    @Override
    public boolean deleteRemarkById(String id) {
        return activityRemarkDao.deleteRemarkById(id) == 1;
    }

    @Override
    public boolean saveRemark(ActivityRemark activityRemark) {
        return activityRemarkDao.saveRemark(activityRemark) == 1;
    }

    @Override
    public boolean updateRemark(ActivityRemark activityRemark) {
        return activityRemarkDao.updateRemark(activityRemark)==1;
    }
}
