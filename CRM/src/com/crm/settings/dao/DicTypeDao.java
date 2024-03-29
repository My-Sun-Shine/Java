package com.crm.settings.dao;

import com.crm.settings.domain.DicType;

import java.util.List;

/**
 * @Classname DicTypeDao
 * @Date 2020/3/20 22:05
 * @Created by Falling Stars
 * @Description
 */
public interface DicTypeDao {
    int checkCode(String code);

    int saveDicType(DicType dicType);

    List<DicType> getDicTypeList();
}
