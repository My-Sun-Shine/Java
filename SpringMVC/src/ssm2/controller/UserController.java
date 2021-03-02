package ssm2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ssm2.beans.User;
import ssm2.service.UserService;


@Controller
@RequestMapping("/user")
public class UserController {

    //引用类型 自动注入（@Autowired ,@Resource）
    //byType
    @Autowired
    private UserService userService;


    //浏览全部学生
    @RequestMapping("/listUser.do")
    @ResponseBody
    public List<User> queryStudent() {
        return userService.queryUsers();
    }
}
