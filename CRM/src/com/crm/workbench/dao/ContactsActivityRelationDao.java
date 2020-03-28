package com.crm.workbench.dao;

import com.crm.workbench.domain.ContactsActivityRelation; /**
 * @Classname ContactsActivityRelationDao
 * @Date 2020/3/28 17:14
 * @Created by Falling Stars
 * @Description 联系人和市场活动的关联关系表
 */
public interface ContactsActivityRelationDao {
    int saveRelation(ContactsActivityRelation contactsActivityRelation);
}
