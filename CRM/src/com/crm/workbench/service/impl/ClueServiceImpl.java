package com.crm.workbench.service.impl;

import com.crm.utils.DateTimeUtil;
import com.crm.utils.SqlSessionUtil;
import com.crm.utils.UUIDUtil;
import com.crm.vo.PaginationVO;
import com.crm.workbench.dao.*;
import com.crm.workbench.domain.*;
import com.crm.workbench.service.ClueService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname ClueServiceImpl
 * @Date 2020/3/25 21:58
 * @Created by Falling Stars
 * @Description 线索表业务层接口实现
 */
public class ClueServiceImpl implements ClueService {
    ActivityDao activityDao = (ActivityDao) SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);

    ClueDao clueDao = (ClueDao) SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
    ClueRemarkDao clueRemarkDao = (ClueRemarkDao) SqlSessionUtil.getSqlSession().getMapper(ClueRemarkDao.class);
    ClueActivityRelationDao clueActivityRelationDao = (ClueActivityRelationDao) SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);

    CustomerDao customerDao = (CustomerDao) SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);
    CustomerRemarkDao customerRemarkDao = (CustomerRemarkDao) SqlSessionUtil.getSqlSession().getMapper(CustomerRemarkDao.class);

    ContactsDao contactsDao = (ContactsDao) SqlSessionUtil.getSqlSession().getMapper(ContactsDao.class);
    ContactsRemarkDao contactsRemarkDao = (ContactsRemarkDao) SqlSessionUtil.getSqlSession().getMapper(ContactsRemarkDao.class);
    ContactsActivityRelationDao contactsActivityRelationDao = (ContactsActivityRelationDao) SqlSessionUtil.getSqlSession().getMapper(ContactsActivityRelationDao.class);

    TranDao tranDao = (TranDao) SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    TranHistoryDao tranHistoryDao = (TranHistoryDao) SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);

    @Override
    public boolean saveClue(Clue clue) {
        return clueDao.saveClue(clue) == 1;
    }

    @Override
    public PaginationVO<Clue> pageList(Map<String, Object> map) {

        int total = clueDao.getTotalByCondition(map);
        List<Clue> clueList = clueDao.getListByCondition(map);
        PaginationVO<Clue> paginationVO = new PaginationVO<>();
        paginationVO.setTotal(total);
        paginationVO.setDataList(clueList);
        return paginationVO;
    }

    @Override
    public Clue detailClue(String id) {
        return clueDao.detailClue(id);
    }

    @Override
    public List<Activity> getActivityByClueId(String clueId) {
        return activityDao.getActivityByClueId(clueId);
    }

    @Override
    public boolean deleteRelation(String id) {
        return clueActivityRelationDao.deleteRelation(id) == 1;
    }

    @Override
    public List<Activity> getActivityListByNameAndNotByClueId(String clueId, String aname) {
        Map<String, String> map = new HashMap<>();
        map.put("aname", aname);
        map.put("clueId", clueId);
        return activityDao.getActivityListByNameAndNotByClueId(map);
    }

    @Override
    public boolean bund(String clueId, String[] aIds) {
        boolean flag = true;
        for (String aId : aIds) {
            ClueActivityRelation clueActivityRelation = new ClueActivityRelation();
            clueActivityRelation.setId(UUIDUtil.getUUID());
            clueActivityRelation.setClueId(clueId);
            clueActivityRelation.setActivityId(aId);
            int count = clueActivityRelationDao.saveRelationDao(clueActivityRelation);
            if (count != 1) {
                flag = false;
            }
        }

        return flag;
    }

    @Override
    public boolean convert(String clueId, Tran tran, String createBy) {
        boolean flag = true;

        //(1) 获取到线索id，通过线索id获取线索对象（线索对象当中封装了线索的信息）线索对象的作用是，将里面封装的信息转换到客户和联系人中。
        Clue clue = clueDao.getClueById(clueId);

        //(2) 通过线索对象提取客户名称，当该客户不存在的时候，新建客户（根据公司的名称精确匹配，判断该客户是否存在！）
        String company = clue.getCompany();
        Customer customer = customerDao.getCustomerByName(company);
        if (customer == null) {
            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customer.setOwner(clue.getOwner());
            customer.setName(company);
            customer.setWebsite(clue.getWebsite());
            customer.setPhone(clue.getPhone());
            customer.setCreateBy(createBy);
            customer.setCreateTime(DateTimeUtil.getSysTime());
            customer.setContactSummary(clue.getContactSummary());
            customer.setNextContactTime(clue.getNextContactTime());
            customer.setDescription(clue.getDescription());
            customer.setAddress(clue.getAddress());
            int count = customerDao.saveCustomer(customer);
            if (count != 1) {
                flag = false;
            }
        }

        //(3) 通过线索对象提取联系人信息，保存联系人
        Contacts contacts = new Contacts();
        contacts.setId(UUIDUtil.getUUID());
        contacts.setOwner(clue.getOwner());
        contacts.setSource(clue.getSource());
        contacts.setCustomerId(customer.getId());
        contacts.setFullname(clue.getFullname());
        contacts.setAppellation(clue.getAppellation());
        contacts.setEmail(clue.getEmail());
        contacts.setMphone(clue.getMphone());
        contacts.setJob(clue.getJob());
        contacts.setCreateBy(createBy);
        contacts.setCreateTime(DateTimeUtil.getSysTime());
        contacts.setDescription(clue.getDescription());
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setAddress(clue.getAddress());
        int count1 = contactsDao.saveContacts(contacts);
        if (count1 != 1) {
            flag = false;
        }

        //(4) 线索备注转换到客户备注以及联系人备注
        List<ClueRemark> clueRemarkList = clueRemarkDao.getClueRemarkListByClueId(clueId);
        for (ClueRemark item : clueRemarkList) {
            //客户备注
            CustomerRemark customerRemark = new CustomerRemark();
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setNoteContent(item.getNoteContent());
            customerRemark.setCreateBy(createBy);
            customerRemark.setCreateTime(DateTimeUtil.getSysTime());
            customerRemark.setEditFlag("0");
            customerRemark.setCustomerId(customer.getId());
            int count9 = customerRemarkDao.saveCustomerRemark(customerRemark);
            if (count9 != 1) {
                flag = false;
            }

            //联系人备注
            ContactsRemark contactsRemark = new ContactsRemark();
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setNoteContent(item.getNoteContent());
            contactsRemark.setCreateBy(createBy);
            contactsRemark.setCreateTime(DateTimeUtil.getSysTime());
            contactsRemark.setEditFlag("0");
            contactsRemark.setContactsId(contacts.getId());
            int count2 = contactsRemarkDao.saveContactsRemark(contactsRemark);
            if (count2 != 1) {
                flag = false;
            }
        }

        //(5) “线索和市场活动”的关系转换到“联系人和市场活动”的关系
        List<ClueActivityRelation> clueActivityRelationList = clueActivityRelationDao.getListByClueId(clueId);
        for (ClueActivityRelation item : clueActivityRelationList) {
            ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setActivityId(item.getActivityId());
            contactsActivityRelation.setContactsId(contacts.getId());//联系人
            int count3 = contactsActivityRelationDao.saveRelation(contactsActivityRelation);
            if (count3 != 1) {
                flag = false;
            }
        }

        //(6) 如果需要创建交易，创建一条交易
        if (tran != null) {
            tran.setOwner(clue.getOwner());
            tran.setCustomerId(customer.getId());
            tran.setSource(clue.getSource());
            tran.setContactsId(contacts.getId());//联系人
            tran.setCreateBy(createBy);
            tran.setCreateTime(DateTimeUtil.getSysTime());
            tran.setDescription(clue.getDescription());
            tran.setContactSummary(clue.getContactSummary());
            tran.setNextContactTime(clue.getNextContactTime());

            int count4 = tranDao.saveTran(tran);
            if (count4 != 1) {
                flag = false;
            } else {
                //(7) 如果创建了交易，则创建一条交易历史
                TranHistory tranHistory = new TranHistory();
                tranHistory.setId(UUIDUtil.getUUID());
                tranHistory.setStage(tran.getStage());
                tranHistory.setMoney(tran.getMoney());
                tranHistory.setExpectedDate(tran.getExpectedDate());
                tranHistory.setCreateTime(DateTimeUtil.getSysTime());
                tranHistory.setCreateBy(createBy);
                tranHistory.setTranId(tran.getId());

                int count5 = tranHistoryDao.saveTranHistory(tranHistory);
                if (count5 != 1) {
                    flag = false;
                }
            }
        }

        // (8) 删除线索备注
        int count6 = clueRemarkDao.deleteByClueId(clueId);
        if (count6 != clueRemarkList.size()) {
            flag = false;
        }

        // (9) 删除线索和市场活动的关系
        int count7 = clueActivityRelationDao.deleteByClueId(clueId);
        if (count7 != clueActivityRelationList.size()) {
            flag = false;
        }

        //(10) 删除线索
        int count8 = clueDao.deleteById(clueId);
        if (count8 != 1) {
            flag = false;
        }

        return flag;
    }

    @Override
    public List<Activity> getActivityListByName(String aname) {
        return activityDao.getActivityListByName(aname);
    }
}
