package com.crm.workbench.dao;

import com.crm.workbench.domain.Contacts; /**
 * @Classname ContactsDao
 * @Date 2020/3/28 17:15
 * @Created by Falling Stars
 * @Description 联系人表
 */
public interface ContactsDao {
    int saveContacts(Contacts contacts);
}
