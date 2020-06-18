package com.test.examine.service.studentService.impl;


import com.github.pagehelper.Page;
import com.test.examine.entity.Student;
import com.test.examine.entity.StudentInfo;
import com.test.examine.mapper.StudentMapper;
import com.test.examine.service.common.CASLabService;
import com.test.examine.service.common.UserService;
import com.test.examine.service.common.impl.UserServiceImpl;
import com.test.examine.service.studentService.StudentService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    private StudentMapper studentMapper;
    private UserService userService;
    private PasswordEncoder encoder;
    private CASLabService casLabService;

    @Autowired
    public StudentServiceImpl(StudentMapper studentMapper, UserService userService, CASLabService casLabService, PasswordEncoder encoder) {
        this.studentMapper = studentMapper;
        this.userService = userService;
        this.encoder = encoder;
        this.casLabService = casLabService;
    }


    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
    public Student findStudentById(String id) {
        Student student = studentMapper.findStudentById(id);
        return student;
    }

    //沿用同一事务
    //读写提交
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int addStudent(Student student) {
        student.setRole("student");
        student.setAuthority("p4");
        student.setEnable(0);
        student.setPassword(encoder.encode(student.getPassword()));
        //用户
        userService.addUser(student);
        //学生
        studentMapper.addStudent(student);
        return 1;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
    public int checkStudent(Student student) {
        if (userService.checkUser(student.getId())) {
            return 1;
        } else {
            if (studentMapper.checkEmail(student.getEmail()) == null) {
                return 3;
            } else {
                return 2;
            }
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public List<Student> findApplies() {
        return studentMapper.findApplies();
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Page<Student> pageApplies() {
        return (Page<Student>) studentMapper.findApplies();
    }


    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int findCount() {
        int total = studentMapper.findCount();
        return total;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public int markStudent(String id) {
        return studentMapper.markStudent(id);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public int unmarkStudent(String id) {
        return studentMapper.unmarkStudent(id);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public List<StudentInfo> findMarked() {
        return studentMapper.findmarked();
    }

    public int findCountByLabid(int labid)
    {
        return studentMapper.findCountByLabid(labid);
    }

    @Override
    public List<StudentInfo> findMarkedByLabid(int labid) {
        return studentMapper.findmarkedByLabid(labid);
    }

    @Override
    public List<Student> findStudentsOnline(int labid, String time) {
        return studentMapper.findStudentsOnline(labid,time);
    }

    @Override
    public List<Student> findStudentsOffline(int labid, String time) {
        return studentMapper.findStudentsOffline(labid,time);
    }
}
