package com.test.examine.controller.adminController;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.test.examine.entity.Admin;
import com.test.examine.entity.HealthAnalyse;
import com.test.examine.entity.Student;
import com.test.examine.service.adminService.AdminService;
import com.test.examine.service.adminService.CASService;
import com.test.examine.service.adminService.HealthAnalyseService;
import com.test.examine.service.adminService.LabService;
import com.test.examine.service.common.CASLabService;
import com.test.examine.service.common.MailService;
import com.test.examine.service.studentService.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.logging.Logger;

@Controller
@RequestMapping("/admin")
@SessionAttributes(names = {"applies", "count"}, types = {Student[].class, Integer.class})
public class AdminController {
    private CASService casService;
    private StudentService studentService;
    private MailService mailService;
    private HealthAnalyseService healthAnalyseService;
    private CASLabService casLabService;
    private AdminService adminService;


    @Autowired
    public AdminController(CASService casService, StudentService studentService, MailService mailService, HealthAnalyseService healthAnalyseService, CASLabService casLabService, AdminService adminService) {
        this.casService = casService;
        this.studentService = studentService;
        this.mailService = mailService;
        this.healthAnalyseService = healthAnalyseService;
        this.casLabService = casLabService;
        this.adminService = adminService;
    }

    @GetMapping
    public ModelAndView getLogin() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("list", healthAnalyseService.healthAanlyse());
        modelAndView.setViewName("admin/index");
        return modelAndView;
    }

    @GetMapping("/checkApply")
    public String checkApply(@RequestParam(defaultValue = "1") int index, Model model) {

        PageHelper.startPage(index, 15);
        PageInfo<Student> studentPageInfo = new PageInfo<>(studentService.pageApplies());
        List<Student> applies = studentPageInfo.getList();
        model.addAttribute("applies", applies);
        model.addAttribute("total", studentPageInfo.getPages());
        model.addAttribute("next", studentPageInfo.getNextPage());
        model.addAttribute("current", studentPageInfo.getPageNum());
        model.addAttribute("pre", studentPageInfo.getPrePage());
        model.addAttribute("count", 0);
        return "admin/check_apply";
    }

    @PostMapping("/handleApply")
    @ResponseBody
    public String handleApply(@RequestBody Student student, Model model) {
        //比较并更改
        if (student.getEnable() == 1) {
            //先实验室
            if (casLabService.CASDecrease(student.getLabid()) == 1) {
                //如果学生已成功激活,表示当前学生是由当前线程激活的
                if (casService.CASEnable(student.getId()) == 1) {
                    //通知用户
                    mailService.sendMail(student.getEmail());
                    int count = (Integer) model.getAttribute("count");
                    model.addAttribute("count", count + 1);
                    if (count == 14) {
                        return "/admin/checkApply";
                    }
                    return "success";
                }
                //如果学生没有成功激活,表示当前学生是其它线程激活的
                else {
                    //返还之前减少的实验室数量
                    casLabService.CASReturn(student.getLabid());
                }
            }
            //如果实验室已经不够
            else {
                return "该实验室已满,激活失败";
            }
        } else {
            casService.CASDelete(student.getId());
            int count = (Integer) model.getAttribute("count");
            model.addAttribute("count", count + 1);
            if (count == 14) {
                return "/admin/checkApply";
            }
        }
        return "success";
    }

    @RequestMapping("/personal")
    public ModelAndView personal() {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        Admin admin = adminService.findAdminById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("admin", admin);
        modelAndView.setViewName("admin/personal");
        return modelAndView;
    }


}
