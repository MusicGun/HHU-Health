package com.test.examine.mapper;


import com.test.examine.entity.Lab;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LabMapper {


    @Update("update lab set capacity=#{value} where labid=#{labid} and capacity=#{pre} and #{value}>=0")
    int CASupdate(@Param("labid") int labid, @Param("pre") int pre, @Param("value") int value);

    @Select("select capacity from lab where labid=#{labid}")
    int findCapacity(@Param("labid") int labid);

    @Select("select * from lab")
    List<Lab> findlabs();

    @Select("select * from lab where labid=#{labid}")
    Lab findLabByLabid(@Param("labid")int labid);

    @Update("update lab set id=#{id} where labid=#{labid}")
    int updateLabAdmin(@Param("labid") int labid, @Param("id") String id);

    @Update("update lab set total=#{t},capacity=#{c} where labid=#{labid} and total=#{pret} and capacity=#{prec}")
    int addTotal(@Param("t") int t, @Param("c") int c, @Param("labid") int labid, @Param("pret") int pret, @Param("prec") int prec);

    @Select("select * from lab where id=#{id}")
    Lab findLabById(@Param("id")String id);


}
