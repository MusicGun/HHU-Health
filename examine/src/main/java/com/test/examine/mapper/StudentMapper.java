package com.test.examine.mapper;

import com.test.examine.entity.Student;
import com.test.examine.entity.StudentInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StudentMapper {


    @Select("select * from student where id=#{id}")
    Student findStudentById(@Param("id") String id);

    @Insert("insert into student (id,email,name,sex,labid,dormitory,phone) " +
            "values(#{s.id},#{s.email},#{s.name},#{s.sex},#{s.labid},#{s.dormitory},#{s.phone})")
    int addStudent(@Param("s") Student student);

    @Select("select email from student where email=#{email}")
    String checkEmail(@Param("email") String email);


    @Select("select student.id,name,phone,email,labid,dormitory from student join user on user.id=student.id" +
            " and enable=0")
    List<Student> findApplies();

    @Select("select count(distinct student.id) from student inner join user on student.id=user.id and user.enable=1;")
    int findCount();


    @Select("select id,name,phone,email,dormitory,labid from student where mark=1")
    List<StudentInfo> findmarked();

    @Update("update student set mark=1 where mark=0 and id=#{id}")
    int markStudent(@Param("id") String id);

    @Update("update student set mark=0 where mark=1 and id=#{id}")
    int unmarkStudent(@Param("id") String id);


    @Select("select count(student.id) from student join user on user.id=student.id and labid=#{labid} and enable=1")
    int findCountByLabid(@Param("labid") int labid);


    @Select("select id,name,phone,email,dormitory,labid from student where mark=1 and labid=#{labid}")
    List<StudentInfo> findmarkedByLabid(int labid);

    //查询今日已上报学生
    @Select("select student.id,name from student join health_info on student.id=health_info.id and labid=#{labid} and left(inTime,10)=#{time}")
    List<Student> findStudentsOnline(@Param("labid") int labid, @Param("time") String time);


    @Select("select student.id,name from student where labid=#{labid} and student.id not in (" +
            "select health_info.id from health_info where left(inTime,10)=#{time})" + "and id in(select id from user where enable=1)")
    List<Student> findStudentsOffline(@Param("labid") int labid, @Param("time") String time);
}
