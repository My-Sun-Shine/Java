package com.crm.workbench.web.controller;

import com.crm.settings.domain.User;
import com.crm.utils.DateTimeUtil;
import com.crm.utils.PrintJson;
import com.crm.utils.ServiceFactory;
import com.crm.utils.UUIDUtil;
import com.crm.vo.PaginationVO;
import com.crm.workbench.domain.Clue;
import com.crm.workbench.service.ClueService;
import com.crm.workbench.service.impl.ClueServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Classname ClueController
 * @Date 2020/3/25 22:00
 * @Created by Falling Stars
 * @Description 线索表控制器
 */
@WebServlet(urlPatterns = {"/settings/clue/saveClue.do", "/workbench/clue/pageList.do", "/workbench/clue/detailClue.do"})
public class ClueController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/settings/clue/saveClue.do".equals(path)) {
            saveClue(req, resp);
        } else if ("/workbench/clue/pageList.do".equals(path)) {
            pageList(req, resp);
        } else if ("/workbench/clue/detailClue.do".equals(path)) {
            detailClue(req, resp);
        }
    }

    /**
     * 进入查询线索详情页面操作
     *
     * @param req
     * @param resp
     */
    private void detailClue(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入查询线索详情页面操作");
        ClueService service = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        String id = req.getParameter("id");
        Clue clue = service.detailClue(id);
        req.setAttribute("clue", clue);
        req.getRequestDispatcher("/workbench/clue/detail.jsp").forward(req, resp);
    }

    /**
     * 分页查询数据操作
     *
     * @param req
     * @param resp
     */
    private void pageList(HttpServletRequest req, HttpServletResponse resp) {
        ClueService service = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        //分页参数
        String pageNoStr = req.getParameter("pageNo");
        String pageSizeStr = req.getParameter("pageSize");
        int pageNo = Integer.valueOf(pageNoStr);
        int pageSize = Integer.valueOf(pageSizeStr);
        int skipCount = (pageNo - 1) * pageSize;//跳过的记录数

        String fullname = req.getParameter("fullname");
        String owner = req.getParameter("owner");
        String company = req.getParameter("company");
        String phone = req.getParameter("phone");
        String mphone = req.getParameter("mphone");
        String state = req.getParameter("state");
        String source = req.getParameter("source");

        Map<String, Object> map = new HashMap<>();
        map.put("fullname", fullname);
        map.put("company", company);
        map.put("phone", phone);
        map.put("mphone", mphone);
        map.put("owner", owner);
        map.put("state", state);
        map.put("source", source);
        map.put("skipCount", skipCount);
        map.put("pageSize", pageSize);
        PaginationVO<Clue> result = service.pageList(map);
        PrintJson.printJsonObj(resp, result);
    }

    /**
     * 进入保存线索操作
     *
     * @param req
     * @param resp
     */
    private void saveClue(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("进入保存线索操作");
        String id = UUIDUtil.getUUID();
        String fullname = req.getParameter("fullname");
        String appellation = req.getParameter("appellation");
        String owner = req.getParameter("owner");
        String company = req.getParameter("company");
        String job = req.getParameter("job");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String website = req.getParameter("website");
        String mphone = req.getParameter("mphone");
        String state = req.getParameter("state");
        String source = req.getParameter("source");
        String createBy = ((User) req.getSession().getAttribute("user")).getId();
        String createTime = DateTimeUtil.getSysTime();
        String description = req.getParameter("description");
        String contactSummary = req.getParameter("contactSummary");
        String nextContactTime = req.getParameter("nextContactTime");
        String address = req.getParameter("address");

        Clue clue = new Clue();
        clue.setId(id);
        clue.setFullname(fullname);
        clue.setAppellation(appellation);
        clue.setOwner(owner);
        clue.setCompany(company);
        clue.setJob(job);
        clue.setEmail(email);
        clue.setPhone(phone);
        clue.setWebsite(website);
        clue.setMphone(mphone);
        clue.setState(state);
        clue.setSource(source);
        clue.setCreateBy(createBy);
        clue.setCreateTime(createTime);
        clue.setDescription(description);
        clue.setContactSummary(contactSummary);
        clue.setNextContactTime(nextContactTime);
        clue.setAddress(address);
        ClueService service = (ClueService) ServiceFactory.getService(new ClueServiceImpl());
        boolean flag = service.saveClue(clue);
        PrintJson.printJsonFlag(resp, flag);
    }
}
