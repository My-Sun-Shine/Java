package com.crm.workbench.service.impl;

import com.crm.utils.SqlSessionUtil;
import com.crm.workbench.dao.ClueDao;
import com.crm.workbench.domain.Clue;
import com.crm.workbench.service.ClueService;

/**
 * @Classname ClueServiceImpl
 * @Date 2020/3/25 21:58
 * @Created by Falling Stars
 * @Description 线索表业务层接口实现
 */
public class ClueServiceImpl implements ClueService {
    ClueDao clueDao = (ClueDao) SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);

    @Override
    public boolean saveClue(Clue clue) {
        return clueDao.saveClue(clue) == 1;
    }
}
