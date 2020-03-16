package com.Controller;

import com.Service.Impl.TAccountServiceImpl;
import com.Service.Impl.TAccountServiceProxy;
import com.Service.TAccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Classname TAccountController
 * @Date 2020/3/15 21:54
 * @Created by Falling Stars
 * @Description 转账项目控制器
 */
@WebServlet(urlPatterns = "/com/Controller/TAccount")
public class TAccountController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //super.doGet(req, resp);
        System.out.println("开始执行转账操作");

        //接收表单参数
        String outAccount = request.getParameter("outAccount");//转出账号
        String intoAccount = request.getParameter("intoAccount");//转入账号
        String BalanceStr = request.getParameter("Balance");//金额

        TAccountServiceImpl accountServiceImpl = new TAccountServiceImpl();
        TAccountService accountService=new TAccountServiceProxy(accountServiceImpl);
        boolean result = accountService.tAccount(outAccount, intoAccount, BalanceStr);
        PrintWriter writer = response.getWriter();
        writer.print(result);
        writer.close();
    }
}
