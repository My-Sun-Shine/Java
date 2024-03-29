package com.crm.workbench.dao;

import com.crm.workbench.domain.Customer;

import java.util.List;

/**
 * @Classname Customer
 * @Date 2020/3/28 17:16
 * @Created by Falling Stars
 * @Description 客户表
 */
public interface CustomerDao{
    Customer getCustomerByName(String name);

    int saveCustomer(Customer customer);

    List<String> getCustomerListByName(String name);
}
