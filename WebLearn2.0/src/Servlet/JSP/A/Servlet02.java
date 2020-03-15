package Servlet.JSP.A;

import Models.A.Student;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname Servlet02
 * @Date 2020/3/14 21:50
 * @Created by Falling Stars
 * @Description ajax练习，获取json数据
 */
@WebServlet(urlPatterns = "/Servlet/JSP/A/Servlet02")
public class Servlet02 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        Student student1 = new Student("A0111", "詹三", 23);
        Student student2 = new Student("A0112", "王五", 50);
        Student student3 = new Student("A0113", "李四", 20);
        List list = new ArrayList();
        list.add(student1);
        list.add(student2);
        list.add(student3);
        JSONArray jsonArray = JSONArray.fromObject(list);
        resp.setHeader("Content-type", "text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        writer.print(jsonArray);
        writer.close();
    }
}
