package com.crm.workbench.service;

import com.crm.vo.PaginationVO;
import com.crm.workbench.domain.Tran;
import com.crm.workbench.domain.TranHistory;

import java.util.List;
import java.util.Map;

/**
 * @Classname TranService
 * @Date 2020/3/29 15:31
 * @Created by Falling Stars
 * @Description
 */
public interface TranService {
    boolean saveTransaction(Tran tran);

    PaginationVO<Tran> pageList(Map<String, Object> map);

    Tran detailTran(String id);

    List<TranHistory> getTranHistoryByTranId(String tranId);
}
