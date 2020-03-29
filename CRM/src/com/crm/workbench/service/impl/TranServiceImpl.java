package com.crm.workbench.service.impl;

import com.crm.utils.SqlSessionUtil;
import com.crm.workbench.dao.TranDao;
import com.crm.workbench.service.TranService;

/**
 * @Classname TranServiceImpl
 * @Date 2020/3/29 15:31
 * @Created by Falling Stars
 * @Description
 */
public class TranServiceImpl implements TranService {
    TranDao tranDao = (TranDao) SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
}
