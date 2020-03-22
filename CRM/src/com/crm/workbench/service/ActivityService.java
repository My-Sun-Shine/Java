package com.crm.workbench.service;

import com.crm.workbench.domain.Activity;

/**
 * @Classname ActivityService
 * @Date 2020/3/22 16:13
 * @Created by Falling Stars
 * @Description 市场活动业务层
 */
public interface ActivityService {
    boolean saveActivity(Activity activity);
}
