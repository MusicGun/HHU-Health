package com.test.examine.controller.studentController;


import com.test.examine.entity.HealthInfo;
import com.test.examine.service.studentService.CheckService;
import com.test.examine.service.studentService.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/health")
public class FormController {


    private CheckService checkService;
    private StudentService studentService;

    @Autowired
    public FormController(CheckService checkService, StudentService studentService) {
        this.checkService = checkService;
        this.studentService = studentService;
    }

    @GetMapping("/form")
    public String form() {
        return "student/form";
    }

    @PostMapping("/form")
    public String postForm(@Valid HealthInfo healthInfo, Errors errors) {
        if (errors.hasErrors()) {
            return "error/404";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        healthInfo.setId(authentication.getName());
        int flag = checkService.checkInService(healthInfo);
        if (flag == 1) {
            return "student/form_success";
        } else if (flag == 2) {
            return "student/form_exception";
        } else {
            return "student/form_fail";
        }
    }

    @GetMapping("/success")
    public String success() {
        return "student/form_success";
    }

    @GetMapping("/out")
    public ModelAndView out(@RequestParam("labid") int labid) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (labid != studentService.findStudentById(authentication.getName()).getLabid()) {
            modelAndView.addObject("msg", "您貌似不属于该实验室");
            modelAndView.setViewName("student/out_fail");
            return modelAndView;
        }
        int flag = checkService.checkOutService(authentication.getName());
        if (flag == 1) {
            modelAndView.setViewName("student/out_success");
            return modelAndView;
        } else {
            if (flag == 0) {
                modelAndView.addObject("msg", "您似乎两个小时内并没有登记哦");
                modelAndView.setViewName("student/out_fail");
            } else {
                modelAndView.addObject("msg", "您似乎两个小时内已经登记外出了哦");
                modelAndView.setViewName("student/out_fail");
            }
        }
        return modelAndView;
    }
}
