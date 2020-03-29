package com.crm.workbench.service;

import com.crm.workbench.domain.Contacts;
import java.util.List;

/**
 * @Classname CustomerService
 * @Date 2020/3/29 16:42
 * @Created by Falling Stars
 * @Description
 */
public interface CustomerService {
    List<String> getCustomerListByName(String name);

    List<Contacts> getContactsListByName(String cname);
}
