package ssm2.dao;

import ssm2.beans.User;

import java.util.List;


public interface UserDao {

    List<User> selectUsers();
}
