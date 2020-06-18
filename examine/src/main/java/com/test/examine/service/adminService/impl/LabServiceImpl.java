package com.test.examine.service.adminService.impl;

import com.test.examine.entity.Lab;
import com.test.examine.mapper.LabMapper;
import com.test.examine.service.adminService.LabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LabServiceImpl implements LabService {


    private LabMapper labMapper;

    @Autowired
    public LabServiceImpl(LabMapper labMapper) {
        this.labMapper = labMapper;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public List<Lab> findLabs() {
        return labMapper.findlabs();
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int updateLabAdmin(int labid, String id) {
        return labMapper.updateLabAdmin(labid, id);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int addTotal(int labid, int n) {
        int flag = 0;
        //CAS+自旋
        while (flag == 0) {
            Lab lab = labMapper.findLabByLabid(labid);
            if (lab == null) {
                return 0;
            }
            int pret = lab.getTotal();
            int prec = lab.getCapacity();
            flag = labMapper.addTotal(pret + n, prec + n, labid, pret, prec);
        }
        return flag;
    }

    @Override
    public Lab findLabById(String id) {
        return labMapper.findLabById(id);
    }

    @Override
    public Lab findLabByLabid(int labid) {
        return labMapper.findLabByLabid(labid);
    }
}
