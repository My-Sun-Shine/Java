package com.crm.settings.dao;

import com.crm.settings.domain.DicValue;

import java.util.List;

/**
 * @Classname DicValueDao
 * @Date 2020/3/20 22:05
 * @Created by Falling Stars
 * @Description
 */
public interface DicValueDao {
    int saveDicValue(DicValue dicValue);


    List<DicValue> getListByTypeCode(String code);
}
