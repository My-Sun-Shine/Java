package com.crm.workbench.dao;

import com.crm.workbench.domain.ClueActivityRelation;

/**
 * @Classname ClueActivityRelationDao
 * @Date 2020/3/27 18:59
 * @Created by Falling Stars
 * @Description 市场活动和线索关联
 */
public interface ClueActivityRelationDao {
    int deleteRelation(String id);

    int saveRelationDao(ClueActivityRelation clueActivityRelation);
}
