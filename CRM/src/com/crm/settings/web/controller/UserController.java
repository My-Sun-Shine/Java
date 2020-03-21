package com.crm.settings.web.controller;

import com.crm.settings.domain.User;
import com.crm.settings.service.UserService;
import com.crm.settings.service.impl.UserServiceImpl;
import com.crm.utils.DateTimeUtil;
import com.crm.utils.PrintJson;
import com.crm.utils.ServiceFactory;
import com.crm.utils.UUIDUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname UserController
 * @Date 2020/3/21 22:22
 * @Created by Falling Stars
 * @Description 用户控制器
 */
@WebServlet(urlPatterns = "/settings/qx/user/saveUser.do")
public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/settings/qx/user/saveUser.do".equals(path)) {
            saveUser(req, resp);
        }
    }

    private void saveUser(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("进入添加用户操作");
        String id = UUIDUtil.getUUID();
        String loginAct = req.getParameter("loginAct");
        String name = req.getParameter("name");
        String loginPwd = req.getParameter("loginPwd");
        String email = req.getParameter("email");
        String expireTime = req.getParameter("expireTime");
        String lockState = req.getParameter("lockState");
        String deptno = req.getParameter("deptno");
        String allowIps = req.getParameter("allowIps");
        String createTime = DateTimeUtil.getSysTime();
        String createBy = "登录者";
        User user = new User(id, loginAct, name, loginPwd, email, expireTime, lockState, deptno, allowIps
                , createTime, createBy, null, null);
        UserService service = (UserService) ServiceFactory.getService(new UserServiceImpl());
        boolean flag = service.saveUser(user);
        PrintJson.printJsonFlag(resp, flag);

    }
}
