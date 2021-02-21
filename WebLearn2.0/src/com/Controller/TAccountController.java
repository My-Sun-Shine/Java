package com.Controller;

import com.Service.Impl.TAccountServiceImpl;
import com.Service.Impl.TAccountServiceProxy;
import com.Service.TAccountService;
import com.Util.ServiceFactory;
import com.Util.TransactionInvocationHandler;

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

        //普通代理
        //TAccountServiceImpl类中实现具体的方法
        //TAccountServiceProxy为具体的方法外层添加事务
        /*TAccountServiceImpl accountServiceImpl = new TAccountServiceImpl();
        TAccountService accountService=new TAccountServiceProxy(accountServiceImpl);
        boolean result = accountService.tAccount(outAccount, intoAccount, BalanceStr);*/

        /*
         * 动态代理
         * 先创建TAccountServiceImpl对象
         * 然后创建代理对象
         * 在使用代理对象(执行事务)
         * */
        /*TAccountServiceImpl accountServiceImpl = new TAccountServiceImpl();
        TransactionInvocationHandler handler = new TransactionInvocationHandler(accountServiceImpl);
        Object proxy = handler.getProxy();//这相当于TAccountServiceProxy，但这是Object类型，需要禁止转换
        TAccountService accountService = (TAccountService) proxy;
        boolean result = accountService.tAccount(outAccount, intoAccount, BalanceStr);*/


        /*
        * 使用ServiceFactory
        *
        * 使用invocationHandler接口invoke()方法进行JDK的动态代理，重写invoke
        * */
        TAccountServiceImpl accountServiceImpl = new TAccountServiceImpl();
        //这相当于TAccountServiceProxy，但这是Object类型，需要禁止转换
        TAccountService proxy = (TAccountService)ServiceFactory.getService(accountServiceImpl);
        System.out.println(proxy);
        boolean result = proxy.tAccount(outAccount, intoAccount, BalanceStr);


        PrintWriter writer = response.getWriter();
        writer.print(result);
        writer.close();
    }
}
