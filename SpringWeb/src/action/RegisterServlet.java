package action;


import beans.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Classname RegisterServlet
 * @Date 2020/4/6 21:18
 * @Created by Falling Stars
 * @Description
 */
@WebServlet(urlPatterns = "/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求参数
        String name = req.getParameter("name");
        String age = req.getParameter("age");

        //传统方法请求
        /*String configLocation="applicationContext.xml";
        ApplicationContext ctx  = new ClassPathXmlApplicationContext(configLocation);*/

        //加入监听器ContextLoaderListener，自己获取容器
        /*String key = WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE;
        Object attribute = getServletContext().getAttribute(key);
        WebApplicationContext ctx = null;
        if (attribute != null) {
            ctx = (WebApplicationContext) attribute;
        }*/

        WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        System.out.println("spring容器对象ctx:" + ctx);
        //从spring容器中获取Service
        StudentService service = (StudentService) ctx.getBean("studentService");

        Student student = new Student();
        student.setName(name);
        student.setAge(Integer.parseInt(age));
        service.addStudent(student);

        //显示结果页面
        req.getRequestDispatcher("/result.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
