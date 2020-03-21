package com.crm.settings.web.controller;


import com.crm.settings.domain.DicType;
import com.crm.settings.domain.DicValue;
import com.crm.settings.service.DicService;
import com.crm.settings.service.impl.DicServiceImpl;
import com.crm.utils.PrintJson;
import com.crm.utils.ServiceFactory;
import com.crm.utils.UUIDUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * @Classname DicController
 * @Date 2020/3/20 22:13
 * @Created by Falling Stars
 * @Description
 */
@WebServlet(urlPatterns = {"/settings/dictionary/type/checkCode.do", "/settings/dictionary/type/saveDicType.do"
        , "/settings/dictionary/value/addDicValue.do", "/settings/dictionary/value/saveDicValue.do"})
public class DicController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        System.out.println(path);
        if ("/settings/dictionary/type/checkCode.do".equals(path)) {
            checkCode(req, resp);
        } else if ("/settings/dictionary/type/saveDicType.do".equals(path)) {
            saveDicType(req, resp);
        } else if ("/settings/dictionary/value/addDicValue.do".equals(path)) {
            addDicValue(req, resp);
        } else if ("/settings/dictionary/value/saveDicValue.do".equals(path)) {
            saveDicValue(req, resp);
        }
    }

    /**
     * 进入添加数据字典值操作
     *
     * @param req
     * @param resp
     */
    private void saveDicValue(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("进入添加数据字典值操作");
        String id = UUIDUtil.getUUID();
        String value = req.getParameter("value");
        String text = req.getParameter("text");
        String orderNo = req.getParameter("orderNo");
        String typeCode = req.getParameter("typeCode");
        DicValue dicValue = new DicValue(id, value, text, orderNo, typeCode);
        DicService service = (DicService) ServiceFactory.getService(new DicServiceImpl());
        boolean flag = service.saveDicValue(dicValue);
        if (flag) {
            resp.sendRedirect(req.getContextPath() + "/settings/dictionary/value/index.jsp");
        }

    }

    /**
     * 进入加载字典类型操作
     *
     * @param req
     * @param resp
     */
    private void addDicValue(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入加载字典类型操作");
        DicService service = (DicService) ServiceFactory.getService(new DicServiceImpl());
        List<DicType> dicTypeList = service.getDicTypeList();
        req.setAttribute("dicTypeList", dicTypeList);
        req.getRequestDispatcher("save.jsp").forward(req, resp);
    }

    /**
     * 进入添加数据字典类型操作
     *
     * @param req
     * @param resp
     */
    private void saveDicType(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("进入添加数据字典类型操作");
        //req.setCharacterEncoding("utf-8");
        String code = req.getParameter("code");
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        DicService service = (DicService) ServiceFactory.getService(new DicServiceImpl());
        DicType dicType = new DicType(code, name, description);
        System.out.println(dicType);
        boolean flag = service.saveDicType(dicType);
        //传统请求使用重定向
        if (flag) {
            resp.sendRedirect(req.getContextPath() + "/settings/dictionary/type/index.jsp");
        }
    }

    /**
     * 进入验证编码是否重复的操作
     *
     * @param req
     * @param resp
     */
    private void checkCode(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("进入验证编码是否重复的操作");
        String code = req.getParameter("code");
        DicService service = (DicService) ServiceFactory.getService(new DicServiceImpl());
        boolean flag = service.checkCode(code);

        PrintJson.printJsonFlag(resp, flag);
    }
}
