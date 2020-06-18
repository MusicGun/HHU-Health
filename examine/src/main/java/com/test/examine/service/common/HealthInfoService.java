package com.test.examine.service.common;

import com.test.examine.entity.HealthInfo;
import org.springframework.cache.annotation.Cacheable;


import java.util.List;


public interface HealthInfoService {



    //查找历史上报记录

    List<HealthInfo> findHistory(String id);

    //查找最近的上报记录
    HealthInfo findHealthInfoById(String id);


    //进入登入健康信息
    int checkInHealthInfo(HealthInfo healthInfo);


    //外出登记健康信息
    int checkOutHealthInfo(HealthInfo healthInfo);



    List<HealthInfo> findExceptionInfo(String time);

    //统计一天内打卡健康人数
    int findHealthByLabid(String time,int labid);

    //统计异常人数
    int findExceptionByLabid(String time,int labid);

    //统计实验室停留时间
    double findAvgTimeByLabid(String time,int labid);


    List<HealthInfo> findExceptionInfoByLabid(String time,int laid);


    int[] findHealth15(String a,String b);

    int[] findException15(String a,String b);

    Double[] findAvgTime15(String a,String b);

    boolean checkException(HealthInfo healthInfo);


    //找出实验室的指定日期的上报人数
    int findOnlineByTime(String time,int labid);
}
