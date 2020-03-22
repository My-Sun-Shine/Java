package com.crm.workbench.web.controller;

import com.crm.settings.domain.User;
import com.crm.utils.DateTimeUtil;
import com.crm.utils.PrintJson;
import com.crm.utils.ServiceFactory;
import com.crm.utils.UUIDUtil;
import com.crm.workbench.domain.Activity;
import com.crm.workbench.service.ActivityService;
import com.crm.workbench.service.impl.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname ActivityController
 * @Date 2020/3/22 16:15
 * @Created by Falling Stars
 * @Description 市场活动控制器
 */
@WebServlet(urlPatterns = {"/workbench/activity/saveActivity.do"})
public class ActivityController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/workbench/activity/saveActivity.do".equals(path)) {
            saveActivity(req, resp);
        }
    }

    /**
     * 进入保存市场活动操作
     *
     * @param req
     * @param resp
     */
    private void saveActivity(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("进入保存市场活动操作");
        ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        String id = UUIDUtil.getUUID();
        String owner = req.getParameter("owner");
        String name = req.getParameter("name");
        String startDate = req.getParameter("startDate");
        String endDate = req.getParameter("endDate");
        String cost = req.getParameter("cost");
        String description = req.getParameter("description");
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User) req.getSession().getAttribute("user")).getId();
        Activity activity = new Activity(id, owner, name, startDate, endDate, cost, description
                , createTime, createBy, null, null);
        System.out.println(activity);
        boolean flag = service.saveActivity(activity);
        PrintJson.printJsonFlag(resp, flag);

    }
}
