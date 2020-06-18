package com.test.examine.service.adminService.impl;


import com.test.examine.entity.HealthAnalyse;
import com.test.examine.entity.HealthInfo;
import com.test.examine.entity.Lab;
import com.test.examine.service.adminService.HealthAnalyseService;
import com.test.examine.service.adminService.LabService;
import com.test.examine.service.common.HealthInfoService;
import com.test.examine.service.studentService.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class HealthAnalyseServiceImpl implements HealthAnalyseService {


    private StudentService studentService;


    private HealthInfoService healthInfoService;

    private LabService labService;



    @Autowired
    public HealthAnalyseServiceImpl(StudentService studentService, HealthInfoService healthInfoService, LabService labService) {
        this.studentService = studentService;
        this.healthInfoService = healthInfoService;
        this.labService = labService;

    }

    @Override
    public List<HealthAnalyse> healthAanlyse() {
        List<HealthAnalyse> result = new ArrayList<>();
        int total = studentService.findCount();
        String start = "2020-04-01";
        String end = "2020-04-15";
        int[] online = healthInfoService.findHealth15(start, end);
        int[] exception = healthInfoService.findException15(start, end);
        Double[] stayTime = healthInfoService.findAvgTime15(start, end);
        for (int i = 0; i < online.length; i++) {
            if (i < 10) {
                start = "2020-04-0" + (i + 1);
            } else {
                start = "2020-04-" + (i + 1);
            }
            HealthAnalyse healthAnalyse = new HealthAnalyse();
            healthAnalyse.setStayTime(stayTime[i]);
            healthAnalyse.setTime(start.substring(5));
            healthAnalyse.setOffline(total - online[i]);
            healthAnalyse.setHealth(online[i] - exception[i]);
            healthAnalyse.setException(exception[i]);
            result.add(healthAnalyse);

        }
        return result;
    }

    @Override
    public List<HealthAnalyse> healthAnalyseByLabid(int labid) {
        List<HealthAnalyse> result = new ArrayList<>();
        int total = studentService.findCountByLabid(labid);
        int index = 01;
        while (index <= 14) {
            String now;
            if (index < 10) {
                now = "2020-04-0" + index;
            } else {
                now = "2020-04-" + index;
            }
            int online = healthInfoService.findHealthByLabid(now, labid);
            int exception = healthInfoService.findExceptionByLabid(now, labid);
            double stayTime = healthInfoService.findAvgTimeByLabid(now, labid);
            HealthAnalyse healthAnalyse = new HealthAnalyse();
            healthAnalyse.setStayTime(stayTime);
            healthAnalyse.setTime(now.substring(5));
            healthAnalyse.setOffline(total - online);
            healthAnalyse.setHealth(online - exception);
            healthAnalyse.setException(exception);
            result.add(healthAnalyse);
            index++;
        }
        return result;
    }


    @Override
    public HashMap<Lab, Integer> findOnlineByTime(String time) {
        List<Lab> labs = labService.findLabs();
        HashMap<Lab, Integer> hashMap = new HashMap<>();
        labs.forEach(l -> {
            int value = healthInfoService.findOnlineByTime(time, l.getLabid());
            hashMap.put(l, value);
        });
        return hashMap;
    }
}
