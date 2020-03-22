package com.crm.settings.web.controller;

import com.crm.settings.domain.User;
import com.crm.settings.service.UserService;
import com.crm.settings.service.impl.UserServiceImpl;
import com.crm.utils.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname UserController
 * @Date 2020/3/21 22:22
 * @Created by Falling Stars
 * @Description 用户控制器
 */
@WebServlet(urlPatterns = {"/settings/qx/user/saveUser.do", "/settings/user/login.do", "/settings/user/getUserList.do"})
public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/settings/qx/user/saveUser.do".equals(path)) {
            saveUser(req, resp);
        } else if ("/settings/user/login.do".equals(path)) {
            login(req, resp);
        } else if ("/settings/user/getUserList.do".equals(path)) {
            getUserList(req, resp);
        }
    }


    /**
     * 进入获取所有用户操作
     *
     * @param req
     * @param resp
     */
    private void getUserList(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("进入获取所有用户操作");
        UserService service = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> userList = service.getUserList();
        PrintJson.printJsonObj(resp, userList);
    }

    /**
     * 进入登录操作
     *
     * @param req
     * @param resp
     */
    private void login(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("进入登录用户操作");
        String loginAct = req.getParameter("loginAct");
        String loginPwd = req.getParameter("loginPwd");
        loginPwd = MD5Util.getMD5(loginPwd);
        //获取ID地址
        String ip = req.getRemoteAddr();
        UserService service = (UserService) ServiceFactory.getService(new UserServiceImpl());
        try {
            User user = service.login(loginAct, loginPwd, ip);
            req.getSession().setAttribute("user", user);
            PrintJson.printJsonFlag(resp, true);
        } catch (Exception ex) {
            Map<String, Object> map = new HashMap<>();
            map.put("success", false);
            map.put("msg", ex.getMessage());
            PrintJson.printJsonObj(resp, map);
        }

    }

    /**
     * 进入添加用户操作
     *
     * @param req
     * @param resp
     */
    private void saveUser(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("进入添加用户操作");
        String id = UUIDUtil.getUUID();
        String loginAct = req.getParameter("loginAct");
        String name = req.getParameter("name");
        String loginPwd = req.getParameter("loginPwd");
        loginPwd = MD5Util.getMD5(loginPwd);
        String email = req.getParameter("email");
        String expireTime = req.getParameter("expireTime");
        String lockState = req.getParameter("lockState");
        String dept = req.getParameter("dept");
        String allowIps = req.getParameter("allowIps");
        String createTime = DateTimeUtil.getSysTime();
        String createBy = "登录者";
        User user = new User(id, loginAct, name, loginPwd, email, expireTime, lockState, dept, allowIps
                , createTime, createBy, null, null);
        System.out.println(user);
        UserService service = (UserService) ServiceFactory.getService(new UserServiceImpl());
        boolean flag = service.saveUser(user);
        PrintJson.printJsonFlag(resp, flag);

    }
}
