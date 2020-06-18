package com.test.examine.service.adminService.impl;

import com.test.examine.mapper.UserMapper;
import com.test.examine.service.adminService.CASService;
import com.test.examine.service.common.CASLabService;
import com.test.examine.service.studentService.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CASServiceImpl implements CASService {
    private UserMapper userMapper;
    private StudentService studentService;
    private CASLabService casLabService;

    @Autowired
    public CASServiceImpl(UserMapper userMapper, StudentService studentService,CASLabService labService) {
        this.userMapper = userMapper;
        this.studentService = studentService;
        this.casLabService = labService;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int CASEnable(String id) {
        return userMapper.enableUser(id);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public void CASDelete(String id) {
        userMapper.deleteUser(id);
    }
}
