package com.test.examine.service.adminService.impl;


import com.test.examine.entity.Admin;
import com.test.examine.entity.Lab;
import com.test.examine.mapper.AdminMapper;
import com.test.examine.service.adminService.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    private AdminMapper adminMapper;
    @Autowired
    public AdminServiceImpl(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;

    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public List<Admin> findAdmin() {
        return adminMapper.findAdmin();
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Admin findAdminById(String id) {
        return adminMapper.findAdminById(id);
    }
    @Override
    public int updateAuthority(String id, String p) {
        Admin admin = adminMapper.findAdminById(id);
        if (admin == null) {
            return 0;
        }
        //CAS+自旋
        while (true) {
            if (adminMapper.updateAuthority(p, id, admin.getAuthority()) == 1) {
                break;
            } else {
                admin = adminMapper.findAdminById(id);
            }
        }
        return 1;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int addAdmin(Admin admin) {
        return adminMapper.addAdmin(admin);
    }

    @Override
    public boolean checkEmail(String email) {
        return adminMapper.check(email)!=null;
    }
}
