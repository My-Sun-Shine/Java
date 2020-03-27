package com.crm.workbench.service.impl;

import com.crm.utils.SqlSessionUtil;
import com.crm.utils.UUIDUtil;
import com.crm.vo.PaginationVO;
import com.crm.workbench.dao.ActivityDao;
import com.crm.workbench.dao.ClueActivityRelationDao;
import com.crm.workbench.dao.ClueDao;
import com.crm.workbench.domain.Activity;
import com.crm.workbench.domain.Clue;
import com.crm.workbench.domain.ClueActivityRelation;
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
    ClueDao clueDao = (ClueDao) SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
    ActivityDao activityDao = (ActivityDao) SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    ClueActivityRelationDao clueActivityRelationDao = (ClueActivityRelationDao) SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);

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
}
