package com.crm.workbench.domain;

/**
 * @Classname ContactsActivityRelation
 * @Date 2020/3/28 17:03
 * @Created by Falling Stars
 * @Description 联系人和市场活动的关联关系表
 */
public class ContactsActivityRelation {
    private String id;
    private String contactsId;
    private String activityId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContactsId() {
        return contactsId;
    }

    public void setContactsId(String contactsId) {
        this.contactsId = contactsId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }
}
