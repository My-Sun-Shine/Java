package com.crm.workbench.service.impl;

import com.crm.utils.DateTimeUtil;
import com.crm.utils.SqlSessionUtil;
import com.crm.utils.UUIDUtil;
import com.crm.workbench.dao.CustomerDao;
import com.crm.workbench.dao.TranDao;
import com.crm.workbench.dao.TranHistoryDao;
import com.crm.workbench.domain.Customer;
import com.crm.workbench.domain.Tran;
import com.crm.workbench.domain.TranHistory;
import com.crm.workbench.service.TranService;

/**
 * @Classname TranServiceImpl
 * @Date 2020/3/29 15:31
 * @Created by Falling Stars
 * @Description
 */
public class TranServiceImpl implements TranService {
    TranDao tranDao = (TranDao) SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    CustomerDao customerDao = (CustomerDao) SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);
    TranHistoryDao tranHistoryDao = (TranHistoryDao) SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);

    @Override
    public boolean saveTransaction(Tran tran) {
        boolean flag = true;
        //获取客户
        String customerName = tran.getCustomerId();
        Customer customer = customerDao.getCustomerByName(customerName);
        if (customer == null) {
            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setOwner(tran.getOwner());
            customer.setName(customerName);
            customer.setCreateBy(tran.getCreateBy());
            customer.setCreateTime(DateTimeUtil.getSysTime());
            customer.setContactSummary(tran.getContactSummary());
            customer.setNextContactTime(tran.getNextContactTime());
            customer.setDescription(tran.getDescription());
            int count = customerDao.saveCustomer(customer);
            if (count != 1) {
                flag = false;
            }
        }
        tran.setCustomerId(customer.getId());
        int count1 = tranDao.saveTran(tran);
        if (count1 != 1) {
            flag = false;
        } else {
            TranHistory tranHistory = new TranHistory();
            tranHistory.setId(UUIDUtil.getUUID());
            tranHistory.setStage(tran.getStage());
            tranHistory.setMoney(tran.getMoney());
            tranHistory.setExpectedDate(tran.getExpectedDate());
            tranHistory.setCreateTime(DateTimeUtil.getSysTime());
            tranHistory.setCreateBy(tran.getCreateBy());
            tranHistory.setTranId(tran.getId());

            int count2 = tranHistoryDao.saveTranHistory(tranHistory);
            if (count2 != 1) {
                flag = false;
            }
        }
        return flag;
    }
}
