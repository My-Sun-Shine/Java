package com.crm.settings.service;

import com.crm.settings.domain.DicType; /**
 * @Classname DicService
 * @Date 2020/3/20 22:10
 * @Created by Falling Stars
 * @Description 数据字典业务接口
 */
public interface DicService {
    boolean checkCode(String code);

    boolean saveDicType(DicType dicType);
}
