package com.crm.workbench.service.impl;

import com.crm.utils.SqlSessionUtil;
import com.crm.vo.PaginationVO;
import com.crm.workbench.dao.ActivityDao;
import com.crm.workbench.dao.ClueDao;
import com.crm.workbench.domain.Activity;
import com.crm.workbench.domain.Clue;
import com.crm.workbench.service.ClueService;

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
}
