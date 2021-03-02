package ssm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ssm.beans.Student;
import ssm.service.StudentService;


@Controller
@RequestMapping("/student")
public class StudentController {

    //引用类型 自动注入（@Autowired ,@Resource）
    //byType
    @Autowired
    private StudentService studentService;


    //浏览全部学生
    @RequestMapping("/listStudent.do")
    @ResponseBody
    public List<Student> queryStudent() {
        return studentService.queryStudents();
    }
}
