package com.crm.settings.web.controller;

import com.crm.settings.domain.Dept;
import com.crm.settings.service.DeptService;
import com.crm.settings.service.impl.DeptServiceImpl;
import com.crm.utils.PrintJson;
import com.crm.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Classname DeptController
 * @Date 2020/3/21 21:27
 * @Created by Falling Stars
 * @Description 部门控制器
 */
@WebServlet(urlPatterns = {"/settings/dept/saveDept.do", "/settings/dept/getDeptList.do"})
public class DeptController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/settings/dept/saveDept.do".equals(path)) {
            saveDept(req, resp);
        } else if ("/settings/dept/getDeptList.do".equals(path)) {
            getDeptList(req, resp);
        }
    }

    /**
     * 进入获取部门列表操作
     *
     * @param req
     * @param resp
     */
    private void getDeptList(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("进入获取部门列表操作");
        DeptService service = (DeptService) ServiceFactory.getService(new DeptServiceImpl());
        List<Dept> deptList = service.getDeptList();
        PrintJson.printJsonObj(resp, deptList);
    }

    /**
     * 进入保存部门操作
     *
     * @param req
     * @param resp
     */
    private void saveDept(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("进入保存部门操作");
        String no = req.getParameter("no");
        String manager = req.getParameter("manager");
        String name = req.getParameter("name");
        String phone = req.getParameter("phone");
        String description = req.getParameter("description");
        Dept dept = new Dept(no, manager, name, phone, description);
        System.out.println(dept);
        DeptService service = (DeptService) ServiceFactory.getService(new DeptServiceImpl());
        boolean flag = service.saveDept(dept);
        PrintJson.printJsonFlag(resp, flag);
    }
}
