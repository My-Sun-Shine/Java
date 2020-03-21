package com.crm.settings.service;

import com.crm.settings.domain.User; /**
 * @Classname UserService
 * @Date 2020/3/21 22:20
 * @Created by Falling Stars
 * @Description 用户业务层
 */
public interface UserService {
    boolean saveUser(User user);
}
