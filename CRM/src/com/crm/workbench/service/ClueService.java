package com.crm.workbench.service;

import com.crm.vo.PaginationVO;
import com.crm.workbench.domain.Clue;

import java.util.Map; /**
 * @Classname ClueService
 * @Date 2020/3/25 21:58
 * @Created by Falling Stars
 * @Description 线索表业务层
 */
public interface ClueService {
    boolean saveClue(Clue clue);

    PaginationVO<Clue> pageList(Map<String, Object> map);

    Clue detailClue(String id);
}
