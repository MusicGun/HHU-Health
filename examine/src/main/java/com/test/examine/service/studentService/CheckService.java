package com.test.examine.service.studentService;


import com.test.examine.entity.HealthInfo;

public interface CheckService {

    //进入实验室
    int checkInService(HealthInfo healthInfo);
    //登记外出
    int checkOutService(String id);

}
