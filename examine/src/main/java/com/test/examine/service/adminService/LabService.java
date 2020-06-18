package com.test.examine.service.adminService;

import com.test.examine.entity.Lab;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface LabService {



    List<Lab> findLabs();

    //更新管理员
    int updateLabAdmin(int labid,String id);


    //增加总容量
    int addTotal(int labid,int n);
    //查找管理员所管理实验室
    Lab findLabById(String id);


    //根据labid查找实验室
    Lab findLabByLabid(int labid);

}
