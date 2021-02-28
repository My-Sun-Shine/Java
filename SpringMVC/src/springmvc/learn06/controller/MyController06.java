package springmvc.learn06.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springmvc.learn05.beans.Student;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


/**
 * @Classname MyController
 * @Date 2020/4/7 22:49
 * @Created by Falling Stars
 * @Description
 */

/**
 * @Controller： 创建处理器类的对象，处理器能够处理用户的请求。 位置：在类定义的上面
 * 说明：创建处理器类对象， 默认是单例对象
 */
@Controller
@RequestMapping("/learn06")
public class MyController06 {
    /**
     * 处理器方法返回String, 表示视图，和数据无关
     * 1.逻辑名称，需要使用视图解析器
     */
    @RequestMapping(value = "/some1.do")
    public String doSome1(Student student) {
        System.out.println("接收了some1.do的请求 name:" + student.getName() + "|age:" + student.getAge());

        //指定视图逻辑名称， 需要使用视图解析器， 对视图是forward
        return "learn06/show";
    }

    /**
     * 处理器方法返回String, 表示视图，和数据无关
     * 2.视图完整路径，不需要视图解析器
     */
    @RequestMapping(value = "/some2.do")
    public String doSome2(Student student, HttpServletRequest request) {
        System.out.println("接收了some2.do的请求 name:" + student.getName() + "|age:" + student.getAge());
        request.setAttribute("myName", student.getName());
        request.setAttribute("myAge", student.getAge());
        request.setAttribute("myStudent", student);
        //指定视图的完整路径，不能使用视图解析器 , 视图执行 forward
        return "/WEB-INF/view/learn06/show.jsp";
    }


    /**
     * 处理器方法返回void ,没有数据，也没有视图；通过HttpServletResponse输出数据到浏览器
     *
     * @throws IOException
     */
    @RequestMapping(value = "/some3.do")
    public void doSome3(String name, Integer age, HttpServletResponse response) throws IOException {
        System.out.println("接收了some3.do的请求 ");
        //调用Service处理请求。 得到一个结果对象
        Student student = new Student();
        student.setName(name);
        student.setAge(age);


        //把student转为json
        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(student);
        System.out.println("student的json:" + json);

        //response.getWriter().print(json);
        //把json数据输出，通过HttpServletResponse
        PrintWriter pw = response.getWriter();
        pw.print(json);
        pw.flush();
        pw.close();
    }

    /**
     * 处理器方法返对象，对象表示数据。
     * 返回String ,String表示数据。
     * 1.在处理器方法上面加入@ResponseBody, 返回String就是数据
     * 2.在处理器方法上面没有@ResponseBody,返回String就是视图
     * <p>
     * <p>
     * 框架处理返回值是Object--String
     * 1.检查返回值的数据类型（String）,查找HttpMessageConverter接口的实现类对象列表，从中找到一个
     * 能处理这种类型的实现类对象（处理String类型的返回值）。String - StringHttpMessageConverter.
     * <p>
     * 2.使用HttpMessageConverter接口的实现类对象（StringHttpMessageConverter），调用canWrite(),write()
     * 方法把返回值数据，按text/plain;charset=ISO-8859-1进行转换和处理。
     * <p>
     * 3.框架使用@ResponseBody注解把2中的结果数据，通过HttpServletResponse对象输出给浏览器
     * <p>
     * 解决乱码的问题， 需要使用@RequestMapping(produces="指定输出的数据格式和编码")
     */
    @RequestMapping(value = "/some4.do", produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String doSome4() throws IOException {
        System.out.println("接收了some4.do的请求 ");
        return "Hello SpringMVC 北京 ";
    }

    /**
     * 处理器方法返对象，对象表示数据。
     * <p>
     * 框架处理返回值是Object--Student
     * 1.检查返回值的数据类型（String）,查找HttpMessageConverter接口的实现类对象列表，从中找到一个
     * 能处理这种类型的实现类对象（处理Student类型的返回值）。Student - MappingJackson2HttpMessageConverter.
     * <p>
     * 2.使用HttpMessageConverter接口的实现类对象（MappingJackson2HttpMessageConverter），调用canWrite(),write()
     * 方法把返回值数据，按application/json;charset=UTF-8进行转换和处理。
     * <p>
     * 3.框架使用@ResponseBody注解把2中的结果数据，通过HttpServletResponse对象输出给浏览器
     */

    @RequestMapping(value = "/some5.do")
    @ResponseBody
    public Student doSome5(String name, Integer age) {
        System.out.println("接收了some4.do的请求 ");
        Student student = new Student();
        student.setAge(age);
        student.setName("同学:" + name);
        return student;
    }

    /**
     * 处理器方法返对象，对象表示数据。
     * <p>
     * 框架处理返回值是Object--List<Student>
     * 1.检查返回值的数据类型（List）,查找HttpMessageConverter接口的实现类对象列表，从中找到一个
     * 能处理这种类型的实现类对象（处理List类型的返回值）。List - MappingJackson2HttpMessageConverter.
     * <p>
     * 2.使用HttpMessageConverter接口的实现类对象（MappingJackson2HttpMessageConverter），调用canWrite(),write()
     * 方法把返回值数据，按application/json;charset=UTF-8进行转换和处理。
     * <p>
     * 3.框架使用@ResponseBody注解把2中的结果数据，通过HttpServletResponse对象输出给浏览器
     */

    @RequestMapping(value = "/some6.do")
    @ResponseBody
    public List<Student> doSome6() {
        System.out.println("接收了some6.do的请求 ");
        Student student = new Student();
        student.setAge(20);
        student.setName("张三");

        Student stu = new Student();
        stu.setName("李四");
        stu.setAge(30);

        ArrayList students = new ArrayList();
        students.add(stu);//李四
        students.add(student);//张三
        return students;
    }

}
