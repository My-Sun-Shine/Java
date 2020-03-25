package com.crm.workbench.web.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname ClueController
 * @Date 2020/3/25 22:00
 * @Created by Falling Stars
 * @Description 线索表控制器
 */
@WebServlet(urlPatterns = {"/workbench/clue/"})
public class ClueController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
