package com.crm.settings.service.impl;

import com.crm.settings.dao.DicTypeDao;
import com.crm.settings.dao.DicValueDao;
import com.crm.settings.service.DicService;
import com.crm.utils.SqlSessionUtil;

/**
 * @Classname DicServiceImpl
 * @Date 2020/3/20 22:10
 * @Created by Falling Stars
 * @Description 数据字典业务接口实现
 */
public class DicServiceImpl implements DicService {
    private DicTypeDao dicTypeDao = SqlSessionUtil.getSqlSession().getMapper(DicTypeDao.class);
    private DicValueDao dicValueDao = SqlSessionUtil.getSqlSession().getMapper(DicValueDao.class);

}
