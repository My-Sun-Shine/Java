package com.crm.settings.dao;

import com.crm.settings.domain.User; /**
 * @Classname UserDao
 * @Date 2020/3/21 22:20
 * @Created by Falling Stars
 * @Description 用户数据库交互
 */
public interface UserDao {

    int saveUser(User user);
}
