package com.test.examine.controller.studentController;


import com.test.examine.service.studentService.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/index")
public class Test {


    @Autowired
    private StudentService studentService;

    @GetMapping
    public ModelAndView getIndex()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("student/index");
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        modelAndView.addObject("labid",studentService.findStudentById(id).getLabid());
        return modelAndView;
    }

    @PostMapping
    public ModelAndView postIndex()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("student/index");
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        modelAndView.addObject("labid",studentService.findStudentById(id).getLabid());
        return modelAndView;
    }
}
