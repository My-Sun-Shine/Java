package com.crm.workbench.web.controller;

import com.crm.settings.domain.User;
import com.crm.utils.DateTimeUtil;
import com.crm.utils.PrintJson;
import com.crm.utils.ServiceFactory;
import com.crm.utils.UUIDUtil;
import com.crm.vo.PaginationVO;
import com.crm.workbench.domain.Contacts;
import com.crm.workbench.domain.Tran;
import com.crm.workbench.service.CustomerService;
import com.crm.workbench.service.TranService;
import com.crm.workbench.service.impl.CustomerServiceImpl;
import com.crm.workbench.service.impl.TranServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.events.DTD;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname TranController
 * @Date 2020/3/29 15:32
 * @Created by Falling Stars
 * @Description
 */
@WebServlet(urlPatterns = {"/workbench/transaction/getCustomerListByName.do", "/workbench/transaction/getContactsListByName.do", "/workbench/transaction/saveTransaction.do"
        ,"/workbench/transaction/pageList.do"})
public class TranController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/workbench/transaction/getCustomerListByName.do".equals(path)) {
            getCustomerListByName(req, resp);
        } else if ("/workbench/transaction/getContactsListByName.do".equals(path)) {
            getContactsListByName(req, resp);
        } else if ("/workbench/transaction/saveTransaction.do".equals(path)) {
            saveTransaction(req, resp);
        }else if("/workbench/transaction/pageList.do".equals(path)){
            pageList(req,resp);
        }

    }

    /**
     * 分页查询数据操作
     * @param req
     * @param resp
     */
    private void pageList(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("分页查询数据操作");
        TranService tranService = (TranService) ServiceFactory.getService(new TranServiceImpl());
        //分页参数
        String pageNoStr = req.getParameter("pageNo");
        String pageSizeStr = req.getParameter("pageSize");
        int pageNo = Integer.valueOf(pageNoStr);
        int pageSize = Integer.valueOf(pageSizeStr);
        int skipCount = (pageNo - 1) * pageSize;//跳过的记录数

        String name = req.getParameter("name");
        String owner = req.getParameter("owner");
        String customerName = req.getParameter("customerName");
        String stage = req.getParameter("stage");
        String type = req.getParameter("type");
        String contactsName = req.getParameter("contactsName");
        String source = req.getParameter("source");

        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("customerName", customerName);
        map.put("stage", stage);
        map.put("type", type);
        map.put("owner", owner);
        map.put("contactsName", contactsName);
        map.put("source", source);
        map.put("skipCount", skipCount);
        map.put("pageSize", pageSize);

        PaginationVO<Tran> result = tranService.pageList(map);
        PrintJson.printJsonObj(resp, result);
    }


    /**
     * 进入保存交易操作
     *
     * @param req
     * @param resp
     */
    private void saveTransaction(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("进入保存交易操作");
        TranService tranService = (TranService) ServiceFactory.getService(new TranServiceImpl());
        Tran tran = new Tran();
        String id = UUIDUtil.getUUID();
        String owner = req.getParameter("owner");
        String money = req.getParameter("money");
        String name = req.getParameter("name");
        String expectedDate = req.getParameter("expectedDate");
        String customerId = req.getParameter("customerName");
        String stage = req.getParameter("stage");
        String type = req.getParameter("type");
        String source = req.getParameter("source");
        String activityId = req.getParameter("create-activityId");
        String contactsId = req.getParameter("create-contactsId");
        String createBy = ((User) req.getSession().getAttribute("user")).getId();
        String createTime = DateTimeUtil.getSysTime();
        String description = req.getParameter("description");
        String contactSummary = req.getParameter("contactSummary");
        String nextContactTime = req.getParameter("nextContactTime");

        tran.setId(id);
        tran.setOwner(owner);
        tran.setMoney(money);
        tran.setName(name);
        tran.setExpectedDate(expectedDate);
        tran.setCustomerId(customerId);
        tran.setStage(stage);
        tran.setType(type);
        tran.setSource(source);
        tran.setActivityId(activityId);
        tran.setContactsId(contactsId);
        tran.setCreateBy(createBy);
        tran.setCreateTime(createTime);
        tran.setDescription(description);
        tran.setContactSummary(contactSummary);
        tran.setNextContactTime(nextContactTime);
        boolean flag = tranService.saveTransaction(tran);
        if (flag) {
            resp.sendRedirect(req.getContextPath() + "/workbench/transaction/index.jsp");
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
