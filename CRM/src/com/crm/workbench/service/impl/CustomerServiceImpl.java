package com.crm.workbench.service.impl;

import com.crm.utils.SqlSessionUtil;
import com.crm.workbench.dao.ContactsDao;
import com.crm.workbench.dao.CustomerDao;
import com.crm.workbench.domain.Contacts;
import com.crm.workbench.service.CustomerService;

import java.util.List;

/**
 * @Classname CustomerServiceImpl
 * @Date 2020/3/29 16:42
 * @Created by Falling Stars
 * @Description
 */
public class CustomerServiceImpl implements CustomerService {
    CustomerDao customerDao = (CustomerDao) SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);

    ContactsDao contactsDao = (ContactsDao) SqlSessionUtil.getSqlSession().getMapper(ContactsDao.class);

    @Override
    public List<String> getCustomerListByName(String name) {
        return customerDao.getCustomerListByName(name);
    }

    @Override
    public List<Contacts> getContactsListByName(String cname) {
        return contactsDao.getContactsListByName(cname);
    }
}
