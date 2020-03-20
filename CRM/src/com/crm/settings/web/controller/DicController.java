package com.crm.settings.web.controller;

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
@WebServlet(urlPatterns = "/settings/dictionary/")
public class DicController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

    }
}
