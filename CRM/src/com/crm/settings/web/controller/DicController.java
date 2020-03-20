package com.crm.settings.web.controller;

import com.crm.settings.dao.DicTypeDao;
import com.crm.settings.service.DicService;
import com.crm.settings.service.impl.DicServiceImpl;
import com.crm.utils.PrintJson;
import com.crm.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname DicController
 * @Date 2020/3/20 22:13
 * @Created by Falling Stars
 * @Description
 */
@WebServlet(urlPatterns = {"/settings/dictionary/type/checkCode.do"})
public class DicController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        System.out.println(path);
        if ("/settings/dictionary/type/checkCode.do".equals(path)) {
            checkCode(req, resp);
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
