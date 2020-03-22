package com.crm.settings.dao;

import com.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

/**
 * @Classname UserDao
 * @Date 2020/3/21 22:20
 * @Created by Falling Stars
 * @Description 用户数据库交互
 */
public interface UserDao {

    int saveUser(User user);

    User login(Map<String, String> map);

    List<User> getUserList();
}
