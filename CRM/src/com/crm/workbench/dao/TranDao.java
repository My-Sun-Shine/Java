package com.crm.workbench.dao;

import com.crm.workbench.domain.Tran; /**
 * @Classname Tran
 * @Date 2020/3/28 17:18
 * @Created by Falling Stars
 * @Description 交易表
 */
public interface TranDao {
    int saveTran(Tran tran);
}
