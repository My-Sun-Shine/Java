package com.crm.workbench.dao;

import com.crm.workbench.domain.TranHistory;

import java.util.List;

/**
 * @Classname TranHistory
 * @Date 2020/3/28 17:18
 * @Created by Falling Stars
 * @Description 交易历史表
 */
public interface TranHistoryDao {
    int saveTranHistory(TranHistory tranHistory);

    List<TranHistory> getTranHistoryByTranId(String tranId);
}
