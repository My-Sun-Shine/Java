package E;

import D.Student;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname Servlet07
 * @Date 2020/3/13 13:50
 * @Created by Falling Stars
 * @Description 服务端产生高级类型对象交给异步请求对象，转换为JSON
 */
@WebServlet(urlPatterns = "/E/Servlet07")
public class Servlet07 extends HttpServlet {
    /**
     * 必须将高级类型对象转换为JSON格式数据对象，才可以被异步请求对象获取
     * JSON工具包放在Tomcat的lib下面
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        //m2(resp);
        //m1(resp);
        m3(resp);

    }

    /**
     * 将对象集合转换为JSON数组
     *
     * @param resp
     * @throws IOException
     */
    private void m3(HttpServletResponse resp) throws IOException {
        List list = new ArrayList();
        list.add(new Student(12, "aaa", 13));
        list.add(new Student(11, "bbb", 13));
        list.add(new Student(10, "ccc", 15));

        JSONArray jsonArray = JSONArray.fromObject(list);
        System.out.println(jsonArray);
        resp.getWriter().print(jsonArray);
    }

    /**
     * 使用JSON工具，将类转换为JSON字符串
     *
     * @param resp
     * @throws IOException
     */
    private void m2(HttpServletResponse resp) throws IOException {
        Student student = new Student(12, "aaa", 13);
        JSONObject jsonObject = JSONObject.fromObject(student);
        System.out.println(jsonObject);
        resp.getWriter().print(jsonObject);
    }

    private void m1(HttpServletResponse resp) throws IOException {
        //只有标准格式的JSON，才能推送给异步请求对象
        String jsonObj = "{\"id\":\"10\",\"name\":\"mike\",\"age\":\"13\"}";

        resp.getWriter().print(jsonObj);
    }
}
