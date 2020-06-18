package com.test.examine.service.common;


import com.test.examine.entity.User;



public interface UserService{


    User findUserById(String id);

    //检查用户是否已存在
    boolean checkUser(String id);

    int addUser(User user);

    int deleteUser(String id);

    int deleteAdmin(String id);
}
