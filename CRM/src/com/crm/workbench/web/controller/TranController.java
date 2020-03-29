package com.crm.workbench.web.controller;

import com.crm.utils.PrintJson;
import com.crm.utils.ServiceFactory;
import com.crm.workbench.domain.Contacts;
import com.crm.workbench.service.CustomerService;
import com.crm.workbench.service.impl.CustomerServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Classname TranController
 * @Date 2020/3/29 15:32
 * @Created by Falling Stars
 * @Description
 */
@WebServlet(urlPatterns = {"/workbench/transaction/getCustomerListByName.do", "/workbench/transaction/getContactsListByName.do"})
public class TranController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/workbench/transaction/getCustomerListByName.do".equals(path)) {
            getCustomerListByName(req, resp);
        } else if ("/workbench/transaction/getContactsListByName.do".equals(path)) {
            getContactsListByName(req, resp);
        }

    }

    /**
     * 进入到根据联系人的名称查询联系人列表
     *
     * @param req
     * @param resp
     */
    private void getContactsListByName(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("进入到根据联系人的名称查询联系人列表");
        CustomerService service = (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());
        String cname = req.getParameter("cname");
        List<Contacts> contactsList = service.getContactsListByName(cname);
        PrintJson.printJsonObj(resp, contactsList);

    }

    /**
     * 进入到根据客户的名称查询客户名称列表
     *
     * @param req
     * @param resp
     */
    private void getCustomerListByName(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("进入到根据客户的名称查询客户名称列表");
        CustomerService service = (CustomerService) ServiceFactory.getService(new CustomerServiceImpl());
        String name = req.getParameter("name");
        List<String> nameList = service.getCustomerListByName(name);
        PrintJson.printJsonObj(resp, nameList);
    }


}
