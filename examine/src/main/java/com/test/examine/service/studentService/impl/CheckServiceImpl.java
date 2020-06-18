package com.test.examine.service.studentService.impl;


import com.test.examine.entity.HealthInfo;
import com.test.examine.service.common.HealthInfoService;
import com.test.examine.service.studentService.CheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class CheckServiceImpl implements CheckService {
    private HealthInfoService healthInfoService;

    @Autowired
    public CheckServiceImpl(HealthInfoService healthInfoService) {
        this.healthInfoService = healthInfoService;
    }

    @Override
    public int checkInService(HealthInfo healthInfo) {
        //检查两个小时内是否已进入
        HealthInfo old = healthInfoService.findHealthInfoById(healthInfo.getId());
        LocalDateTime now = LocalDateTime.now();
        if (old != null) {
            if (now.getDayOfMonth() == old.getInTime().getDayOfMonth() && now.getHour() - old.getInTime().getHour() < 2) {
                return 0;
            }
        }
        //如果没有则登记
        healthInfo.setInTime(now);
        healthInfoService.checkInHealthInfo(healthInfo);
        //如果有异常则通告管理员
        if(healthInfoService.checkException(healthInfo))
        {
            return 2;
        }
        return 1;
    }

    @Override
    public int checkOutService(String id) {
        //检查是否两个小时内是否登入
        HealthInfo old = healthInfoService.findHealthInfoById(id);
        LocalDateTime now = LocalDateTime.now();
        if (old != null) {
            //如果已经登入,则可以登记外出
            if (now.getDayOfMonth() == old.getInTime().getDayOfMonth() && now.getHour() - old.getInTime().getHour() < 2 && old.getOutTime() == null) {
                old.setOutTime(now);
                old.setStayTime((now.getHour()-old.getInTime().getHour())*60+(now.getMinute()-old.getInTime().getMinute()));
                healthInfoService.checkOutHealthInfo(old);
                return 1;
            } else if (now.getDayOfMonth() == old.getInTime().getDayOfMonth() && now.getHour() - old.getInTime().getHour() < 2 && old.getOutTime() != null) {
                return 2;
            }
        }
        return 0;
    }
}
