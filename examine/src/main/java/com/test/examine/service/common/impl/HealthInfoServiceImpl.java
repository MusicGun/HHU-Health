package com.test.examine.service.common.impl;


import com.test.examine.entity.Admin;
import com.test.examine.entity.HealthInfo;
import com.test.examine.entity.Lab;
import com.test.examine.entity.Student;
import com.test.examine.mapper.HealthInfoMapper;
import com.test.examine.service.adminService.AdminService;
import com.test.examine.service.adminService.LabService;
import com.test.examine.service.common.HealthInfoService;
import com.test.examine.service.common.MailService;
import com.test.examine.service.studentService.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HealthInfoServiceImpl implements HealthInfoService {

    private HealthInfoMapper healthInfoMapper;

    private LabService labService;

    private StudentService studentService;

    private AdminService adminService;

    private MailService mailService;

    @Autowired
    public HealthInfoServiceImpl(HealthInfoMapper healthInfoMapper, StudentService studentService, LabService labService, AdminService adminService, MailService mailService) {
        this.healthInfoMapper = healthInfoMapper;
        this.studentService = studentService;
        this.adminService = adminService;
        this.labService = labService;
        this.mailService = mailService;
    }


    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public List<HealthInfo> findHistory(String id) {
        return healthInfoMapper.findHistory(id);
    }


    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public HealthInfo findHealthInfoById(String id) {
        return healthInfoMapper.findHealthInfoById(id);
    }

    @Override
    public int checkInHealthInfo(HealthInfo healthInfo) {
        return healthInfoMapper.checkInHealthInfo(healthInfo);
    }

    @Override
    public int checkOutHealthInfo(HealthInfo healthInfo) {
        return healthInfoMapper.checkOutHealthInfo(healthInfo);
    }




    @Override
    public List<HealthInfo> findExceptionInfo(String time) {
        return healthInfoMapper.findExceptionInfo(time);
    }


    @Override
    public int findHealthByLabid(String time, int labid) {
        return healthInfoMapper.findOnlineByLabid(time, labid);
    }


    @Override
    public int findExceptionByLabid(String time, int labid) {
        return healthInfoMapper.findExceptionByLabid(time, labid);
    }


    @Override
    public double findAvgTimeByLabid(String time, int labid) {
        return healthInfoMapper.findAvgTimeByLabid(time, labid);
    }



    @Override
    public List<HealthInfo> findExceptionInfoByLabid(String time, int labid) {
        return healthInfoMapper.findExceptionInfoByLabid(time, labid);
    }

    @Override
    public int[] findHealth15(String a, String b) {
        return healthInfoMapper.findOnline15(a, b);
    }
    @Override
    public int[] findException15(String a, String b) {
        return healthInfoMapper.findException15(a, b);
    }
    @Override
    public Double[] findAvgTime15(String a, String b) {
        return healthInfoMapper.findAvgTime15(a, b);
    }
    @Override
    public boolean checkException(HealthInfo healthInfo) {
        if (!healthInfo.isHealth() || healthInfo.isAT() || healthInfo.isMT() || healthInfo.isVictim() || healthInfo.isStayArea()) {
            //通知管理员
            String id = healthInfo.getId();
            Student student = studentService.findStudentById(id);
            Lab lab = labService.findLabByLabid(student.getLabid());
            Admin admin = adminService.findAdminById(lab.getId());
            mailService.sendException(student.getId() + "," + student.getName() + ",健康异常", admin.getEmail());
            return true;
        }
        return false;
    }


    @Override
    public int findOnlineByTime(String time, int labid) {
        return healthInfoMapper.findOnlineByLabid(time,labid);
    }
}
