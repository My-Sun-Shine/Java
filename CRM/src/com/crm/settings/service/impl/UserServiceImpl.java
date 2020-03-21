package com.crm.settings.service.impl;

import com.crm.settings.dao.UserDao;
import com.crm.settings.domain.User;
import com.crm.settings.service.UserService;
import com.crm.utils.SqlSessionUtil;

/**
 * @Classname UserServiceImpl
 * @Date 2020/3/21 22:20
 * @Created by Falling Stars
 * @Description 用户业务层实现
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public boolean saveUser(User user) {
        return userDao.saveUser(user) == 1;
    }
}
