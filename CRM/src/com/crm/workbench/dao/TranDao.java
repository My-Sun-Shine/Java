package com.crm.workbench.dao;

import com.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map; /**
 * @Classname Tran
 * @Date 2020/3/28 17:18
 * @Created by Falling Stars
 * @Description 交易表
 */
public interface TranDao {
    int saveTran(Tran tran);

    int getTotalByCondition(Map<String, Object> map);

    List<Tran> getListByCondition(Map<String, Object> map);

    Tran detailTran(String id);

    int changeStage(Tran tran);
}
