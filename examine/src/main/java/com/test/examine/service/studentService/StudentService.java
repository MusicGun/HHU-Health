package com.test.examine.service.studentService;


import com.github.pagehelper.Page;
import com.test.examine.entity.Student;
import com.test.examine.entity.StudentInfo;


import java.util.List;


public interface StudentService {


    Student findStudentById(String id);

    int addStudent(Student student);

    int checkStudent(Student student);


    //查找申请请求
    List<Student> findApplies();


    Page<Student> pageApplies();

    //查询学生总数
    int findCount();


    int markStudent(String id);

    int unmarkStudent(String id);


    List<StudentInfo> findMarked();


    int findCountByLabid(int labid);

    List<StudentInfo> findMarkedByLabid(int labid);


    //查找指定实验室指定日期所有上报学生
    List<Student> findStudentsOnline(int labid,String time);

    //查找指定实验室指定日期所有未上报学生
    List<Student> findStudentsOffline(int labid,String time);
}
