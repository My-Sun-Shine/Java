package com.crm.workbench.dao;

import com.crm.workbench.domain.CustomerRemark; /**
 * @Classname CustomerRemarkDao
 * @Date 2020/3/28 17:17
 * @Created by Falling Stars
 * @Description 客户备注表
 */
public interface CustomerRemarkDao {
    int saveCustomerRemark(CustomerRemark customerRemark);
}
