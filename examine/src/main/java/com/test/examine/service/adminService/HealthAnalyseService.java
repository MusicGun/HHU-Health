package com.test.examine.service.adminService;


import com.test.examine.entity.HealthAnalyse;
import com.test.examine.entity.HealthInfo;
import com.test.examine.entity.Lab;
import com.test.examine.entity.Student;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


public interface HealthAnalyseService {

    List<HealthAnalyse> healthAanlyse();

    List<HealthAnalyse> healthAnalyseByLabid(int labid);

    HashMap<Lab,Integer> findOnlineByTime(String time);


}
