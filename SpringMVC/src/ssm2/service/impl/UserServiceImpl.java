package ssm2.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ssm2.beans.User;
import ssm2.dao.UserDao;
import ssm2.service.UserService;


@Service
public class UserServiceImpl implements UserService {

    //引用类型
    //byType
    @Autowired
    private UserDao userDao;


    @Override
    public List<User> queryUsers() {
        List<User> users = userDao.selectUsers();
        return users;

    }

}
