package com.crm.settings.service.impl;

import com.crm.exception.LoginException;
import com.crm.settings.dao.UserDao;
import com.crm.settings.domain.User;
import com.crm.settings.service.UserService;
import com.crm.utils.DateTimeUtil;
import com.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public User login(String loginAct, String loginPwd, String ip) throws LoginException {
        /*
         * 登录业务流程
         *
         * 	调用dao层，传递账号密码，查看账号密码是否正确
         * 		如果正确，返回一个User对象，如果不正确，则抛出异常
         * 		select * from tbl_user where loginAct=#{loginAct} and loginPwd=#{loginPwd}
         *      user==null 不正确，则抛出异常
         *      user!=null 账号密码是正确的
         *      从user对象中  取 失效时间，锁定状态 ，允许访问的ip地址
         *		验证这三项
         *		如果某一项验证失败，则抛出相应的异常信息
         *		如果验证这三项都成功，则不抛出任何异常，最后将user返回到控制器
         *
         */
        Map<String, String> map = new HashMap<>();
        map.put("loginAct", loginAct);
        map.put("loginPwd", loginPwd);
        User user = userDao.login(map);
        if (user == null) {
            throw new LoginException("账号或密码错误");
        }
        String expireTime = user.getExpireTime();
        if (DateTimeUtil.getSysTime().compareTo(expireTime) > 0) {
            throw new LoginException("账号已经失效");
        }

        String allowIps = user.getAllowIps();
        if (!allowIps.contains(ip)) {
            throw new LoginException("登录IP地址受限");
        }

        String lockState = user.getLockState();
        if (lockState.equals("0")) {
            throw new LoginException("账号已锁定");
        }
        return user;
    }

    @Override
    public List<User> getUserList() {
        return userDao.getUserList();
    }

}

