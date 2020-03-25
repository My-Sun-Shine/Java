package com.crm.settings.service;

import com.crm.settings.domain.DicType;
import com.crm.settings.domain.DicValue;

import java.util.List;
import java.util.Map;

/**
 * @Classname DicService
 * @Date 2020/3/20 22:10
 * @Created by Falling Stars
 * @Description 数据字典业务接口
 */
public interface DicService {
    boolean checkCode(String code);

    boolean saveDicType(DicType dicType);

    List<DicType> getDicTypeList();

    boolean saveDicValue(DicValue dicValue);

    Map<String,List<DicValue>> getDicInit();
}
