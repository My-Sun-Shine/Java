package com.crm.workbench.dao;

import com.crm.workbench.domain.Clue; /**
 * @Classname ClueDao
 * @Date 2020/3/25 21:57
 * @Created by Falling Stars
 * @Description 线索表数据库交互
 */
public interface ClueDao {
    int saveClue(Clue clue);
}
