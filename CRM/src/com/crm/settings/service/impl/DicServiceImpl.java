package com.crm.settings.service.impl;

import com.crm.settings.dao.DicTypeDao;
import com.crm.settings.dao.DicValueDao;
import com.crm.settings.domain.DicType;
import com.crm.settings.domain.DicValue;
import com.crm.settings.service.DicService;
import com.crm.utils.SqlSessionUtil;

import java.util.List;

/**
 * @Classname DicServiceImpl
 * @Date 2020/3/20 22:10
 * @Created by Falling Stars
 * @Description 数据字典业务接口实现
 */
public class DicServiceImpl implements DicService {
    private DicTypeDao dicTypeDao = SqlSessionUtil.getSqlSession().getMapper(DicTypeDao.class);
    private DicValueDao dicValueDao = SqlSessionUtil.getSqlSession().getMapper(DicValueDao.class);

    @Override
    public boolean checkCode(String code) {

        return dicTypeDao.checkCode(code) == 0;
    }

    @Override
    public boolean saveDicType(DicType dicType) {
        return dicTypeDao.saveDicType(dicType) == 1;
    }

    @Override
    public List<DicType> getDicTypeList() {
        return dicTypeDao.getDicTypeList();
    }

    @Override
    public boolean saveDicValue(DicValue dicValue) {
        return dicValueDao.saveDicValue(dicValue) == 1;
    }
}
