package com.crm.workbench.dao;

import com.crm.workbench.domain.ClueRemark;

import java.util.List;

/**
 * @Classname ClueRemarkDao
 * @Date 2020/3/28 17:13
 * @Created by Falling Stars
 * @Description 线索备注表
 */
public interface ClueRemarkDao {
    List<ClueRemark> getClueRemarkListByClueId(String clueId);

    int deleteByClueId(String clueId);
}
