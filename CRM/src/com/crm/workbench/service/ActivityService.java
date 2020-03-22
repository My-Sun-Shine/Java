package com.crm.workbench.service;

import com.crm.vo.PaginationVO;
import com.crm.workbench.domain.Activity;

import java.util.Map;

/**
 * @Classname ActivityService
 * @Date 2020/3/22 16:13
 * @Created by Falling Stars
 * @Description 市场活动业务层
 */
public interface ActivityService {
    boolean saveActivity(Activity activity);

    PaginationVO<Activity> pageList(Map<String, Object> map);

    boolean deleteActivity(String[] ids);
}
