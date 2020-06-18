package com.test.examine.service.common.impl;


import com.test.examine.mapper.LabMapper;
import com.test.examine.service.common.CASLabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CASLabServiceImpl implements CASLabService {

    private LabMapper labMapper;

    @Autowired
    public CASLabServiceImpl(LabMapper labMapper) {
        this.labMapper = labMapper;
    }

    //减少容量
    @Override
    public int CASDecrease(int labid) {
        //CAS + 自旋
        //CAS乐观锁,提高并发度
        //自旋提高成功率
        int flag = 0;
        for (int i = 0; i < 100 && flag == 0; i++) {
            int pre = labMapper.findCapacity(labid);
            flag = labMapper.CASupdate(labid, pre, pre - 1);
        }
        return flag;
    }
    //增加容量
    @Override
    public int CASReturn(int labid) {
        //CAS + 自旋
        //CAS乐观锁,提高并发度
        //一直自旋到成功
        int flag = 0;
        while (flag == 0) {
            int pre = labMapper.findCapacity(labid);
            flag = labMapper.CASupdate(labid, pre, pre + 1);
        }
        return flag;
    }
}
