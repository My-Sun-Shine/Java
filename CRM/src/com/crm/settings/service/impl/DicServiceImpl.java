package com.crm.settings.service.impl;

import com.crm.settings.dao.DicTypeDao;
import com.crm.settings.dao.DicValueDao;
import com.crm.settings.domain.DicType;
import com.crm.settings.domain.DicValue;
import com.crm.settings.service.DicService;
import com.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Map<String, List<DicValue>> getDicInit() {
        Map<String, List<DicValue>> map=new HashMap<>();
        List<DicType> dicTypeList=dicTypeDao.getDicTypeList();
        for(DicType dicType:dicTypeList){
            //取得类型编码
            String code=dicType.getCode();
            List<DicValue> dicValueList=dicValueDao.getListByTypeCode(code);
            map.put(code,dicValueList);
        }
        return map;
    }
}
