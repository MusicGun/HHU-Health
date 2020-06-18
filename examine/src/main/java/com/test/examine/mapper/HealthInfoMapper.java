package com.test.examine.mapper;

import com.test.examine.entity.HealthInfo;
import org.apache.ibatis.annotations.*;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface HealthInfoMapper {

    @Insert("insert into health_info (id,mT,aT,health,victim,touchVictim,stayArea,touchFever," +
            "boardHistory,touchForeigner,inTime)" +
            "values(#{h.id},#{h.mT},#{h.aT},#{h.health},#{h.victim},#{h.touchVictim},#{h.stayArea},#{h.touchFever}," +
            "#{h.boardHistory},#{h.touchForeigner},#{h.inTime})")
    int checkInHealthInfo(@Param("h") HealthInfo healthInfo);

    @Select("select * from health_info where id=#{id} order by inTime DESC limit 1")
    HealthInfo findHealthInfoById(@Param("id") String id);

    @Select("select * from health_info where id=#{id} order by inTime DESC")
    List<HealthInfo> findHistory(@Param("id") String id);

    @Update("update health_info set outTime=#{h.outTime},stayTime=#{h.stayTime} where id=#{h.id} and inTime=#{h.inTime}")
    int checkOutHealthInfo(@Param("h") HealthInfo healthInfo);

    @Select("select count(distinct health_info.id) from health_info join student on student.id=health_info.id and labid=#{labid} and left(inTime,10)=#{time}")
    int findOnlineByLabid(@Param("time") String time,@Param("labid")int labid);

    @Select("select count(distinct health_info.id) from health_info join student on student.id=health_info.id and labid=#{labid} and left(inTime,10)=#{time} and (health=0 or touchVictim=1 or boardHistory=1)")
    int findExceptionByLabid(@Param("time")String time,@Param("labid")int labid);

    @Select("select avg(stayTime) from health_info join student on student.id=health_info.id and labid=#{labid} and left(inTime,10)=#{time}")
    Double findAvgTimeByLabid(@Param("time")String time,@Param("labid")int labid);

    @Select("select * from health_info join student on health_info.id=student.id and mark=0 and left(inTime,10)=#{time} and (health=0 or touchVictim=1 or boardHistory=1 or stayArea=1 or touchForeigner=1 or touchFever=1)")
    List<HealthInfo> findExceptionInfo(@Param("time")String time);

    @Select("select * from health_info join student on health_info.id=student.id and mark=0 and labid=#{labid} and left(inTime,10)=#{time} and (health=0 or touchVictim=1 or boardHistory=1 or stayArea=1 or touchForeigner=1 or touchFever=1)")
    List<HealthInfo> findExceptionInfoByLabid(@Param("time")String time,@Param("labid")int labid);

    @Select("select count(distinct id) from health_info where inTime between #{a} and #{b} and (health=0 or touchVictim=1 or boardHistory=1) group by left(inTime,10)")
    int[] findException15(@Param("a")String time,@Param("b") String b);

    @Select("select count(distinct id) from health_info where inTime between #{a} and #{b} group by left(inTime,10)")
    int[] findOnline15(@Param("a") String a,@Param("b")String b);

    @Select("select avg(stayTime) from health_info where inTime between #{a} and #{b} group by left(inTime,10)")
    Double[] findAvgTime15(@Param("a")String a,@Param("b")String b);





}
