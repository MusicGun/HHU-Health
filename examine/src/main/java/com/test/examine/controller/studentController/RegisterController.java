package com.test.examine.controller.studentController;


import com.test.examine.entity.Student;
import com.test.examine.service.studentService.StudentService;
import com.test.examine.service.studentService.impl.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private StudentService studentService;

    @Autowired
    public RegisterController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    @ResponseBody
    public String register(@Valid @RequestBody Student student, Errors errors) {
        System.out.println(student.toString());
        if (errors.hasErrors()) {
            return errors.getAllErrors().get(0).getDefaultMessage();
        }
        int flag = studentService.checkStudent(student);
        if (flag == 1) {
            return "该学号已注册";
        } else if (flag == 2) {
            return "该邮箱已被绑定";
        }
        if (studentService.addStudent(student) == 0) {
            return "该实验室人数已满,请重试";
        }
        return "/register/register_success";
    }

    @GetMapping("/register_success")
    public String registerSuccess() {
        return "student/register_success";
    }

}
