package com.test.examine.service.adminService;


import com.test.examine.entity.Admin;
import com.test.examine.entity.Lab;

import java.util.HashMap;
import java.util.List;

public interface AdminService {


    //查询所有管理员信息
    List<Admin> findAdmin();


    //查询是否存在特定管理员
    Admin findAdminById(String id);

    //更新管理员权限
    int updateAuthority(String id,String p);

    //添加管理员
    int addAdmin(Admin admin);


    //检查管理员邮箱是否已存在
    boolean checkEmail(String email);

}
