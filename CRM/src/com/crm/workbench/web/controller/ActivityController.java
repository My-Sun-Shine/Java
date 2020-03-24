package com.crm.workbench.web.controller;

import com.crm.settings.domain.User;
import com.crm.utils.DateTimeUtil;
import com.crm.utils.PrintJson;
import com.crm.utils.ServiceFactory;
import com.crm.utils.UUIDUtil;
import com.crm.vo.PaginationVO;
import com.crm.workbench.domain.Activity;
import com.crm.workbench.domain.ActivityRemark;
import com.crm.workbench.service.ActivityService;
import com.crm.workbench.service.impl.ActivityServiceImpl;

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
 * @Classname ActivityController
 * @Date 2020/3/22 16:15
 * @Created by Falling Stars
 * @Description 市场活动控制器
 */
@WebServlet(urlPatterns = {"/workbench/activity/saveActivity.do", "/workbench/activity/pageList.do"
        , "/workbench/activity/deleteActivity.do", "/workbench/activity/showEditActivity.do"
        , "/workbench/activity/updateActivity.do", "/workbench/activity/detailActivity.do"
        , "/workbench/activity/getRemarkListById.do"})
public class ActivityController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/workbench/activity/saveActivity.do".equals(path)) {
            saveActivity(req, resp);
        } else if ("/workbench/activity/pageList.do".equals(path)) {
            pageList(req, resp);
        } else if ("/workbench/activity/deleteActivity.do".equals(path)) {
            deleteActivity(req, resp);
        } else if ("/workbench/activity/showEditActivity.do".equals(path)) {
            showEditActivity(req, resp);
        } else if ("/workbench/activity/updateActivity.do".equals(path)) {
            updateActivity(req, resp);
        } else if ("/workbench/activity/detailActivity.do".equals(path)) {
            detailActivity(req, resp);
        } else if ("/workbench/activity/getRemarkListById.do".equals(path)) {
            getRemarkListById(req, resp);
        }
    }

    /**
     * 进入根据市场活动ID获取备注操作
     *
     * @param req
     * @param resp
     */
    private void getRemarkListById(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("进入根据市场活动ID获取备注操作");
        ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        String id = req.getParameter("id");
        List<ActivityRemark> activityRemarkList = service.getRemarkListById(id);
        PrintJson.printJsonObj(resp, activityRemarkList);

    }

    /**
     * 进入获取市场活动详情页面操作
     *
     * @param req
     * @param resp
     */
    private void detailActivity(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入获取市场活动详情页面操作");
        ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        String id = req.getParameter("id");
        Activity activity = service.detailActivity(id);
        req.setAttribute("activity", activity);
        req.getRequestDispatcher("/workbench/activity/detail.jsp").forward(req, resp);
    }

    /**
     * 进入更新市场活动操作
     *
     * @param req
     * @param resp
     */
    private void updateActivity(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("进入更新市场活动操作");

        ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        String id = req.getParameter("id");
        String owner = req.getParameter("owner");
        String name = req.getParameter("name");
        String startDate = req.getParameter("startDate");
        String endDate = req.getParameter("endDate");
        String cost = req.getParameter("cost");
        String description = req.getParameter("description");
        String editTime = DateTimeUtil.getSysTime();
        String editBy = ((User) req.getSession().getAttribute("user")).getId();
        Activity activity = new Activity(id, owner, name, startDate, endDate, cost, description
                , null, null, editTime, editBy);
        System.out.println(activity);
        boolean flag = service.updateActivity(activity);
        PrintJson.printJsonFlag(resp, flag);
    }

    /**
     * 进入获取市场活动编辑页面操作
     *
     * @param req
     * @param resp
     */
    private void showEditActivity(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("进入获取市场活动编辑页面操作");
        ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        String id = req.getParameter("id");
        Map<String, Object> map = service.showEditActivity(id);
        PrintJson.printJsonObj(resp, map);

    }

    /**
     * 进入市场活动删除操作
     *
     * @param req
     * @param resp
     */
    private void deleteActivity(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("进入市场活动删除操作");

        //获取市场活动id数组
        String[] ids = req.getParameterValues("id");
        ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = service.deleteActivity(ids);
        PrintJson.printJsonFlag(resp, flag);
    }

    /**
     * 进入市场活动列表分页查询操作
     *
     * @param req
     * @param resp
     */
    private void pageList(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("进入市场活动列表分页查询操作");
        ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        //分页参数
        String pageNoStr = req.getParameter("pageNo");
        String pageSizeStr = req.getParameter("pageSize");
        int pageNo = Integer.valueOf(pageNoStr);
        int pageSize = Integer.valueOf(pageSizeStr);
        int skipCount = (pageNo - 1) * pageSize;//跳过的记录数
        //查询参数
        String name = req.getParameter("name");
        String owner = req.getParameter("owner");
        String startDate = req.getParameter("startDate");
        String endDate = req.getParameter("endDate");

        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("owner", owner);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("skipCount", skipCount);
        map.put("pageSize", pageSize);
        PaginationVO<Activity> result = service.pageList(map);
        PrintJson.printJsonObj(resp, result);
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
