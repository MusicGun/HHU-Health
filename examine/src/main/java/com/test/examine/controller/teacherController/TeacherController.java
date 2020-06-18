package com.test.examine.controller.teacherController;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.test.examine.entity.*;
import com.test.examine.service.adminService.HealthAnalyseService;
import com.test.examine.service.adminService.LabService;
import com.test.examine.service.adminService.QRService;
import com.test.examine.service.common.HealthInfoService;
import com.test.examine.service.studentService.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController {


    private LabService labService;

    private HealthAnalyseService healthAnalyseService;
    private QRService qrService;

    private HealthInfoService healthInfoService;

    private StudentService studentService;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    @Autowired
    public TeacherController(LabService labService, HealthAnalyseService healthAnalyseService, QRService qrService, HealthInfoService healthInfoService, StudentService studentService) {
        this.healthAnalyseService = healthAnalyseService;
        this.labService = labService;
        this.qrService = qrService;
        this.healthInfoService = healthInfoService;
        this.studentService = studentService;
    }

    @GetMapping
    public ModelAndView index() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("teacher/index");
        String id = (authentication.getName());
        //获取管理的实验室
        Lab lab = labService.findLabById(id);
        if (lab != null) {
            modelAndView.addObject("labid", lab.getLabid());
            //获取该实验室的健康上报信息
            List<HealthAnalyse> list = healthAnalyseService.healthAnalyseByLabid(lab.getLabid());
            modelAndView.addObject("list", list);
        } else {
            modelAndView.addObject("list", null);
        }
        return modelAndView;
    }

    @GetMapping("/QRCode")
    public String qr() {
        return "teacher/QRCode";
    }

    @RequestMapping("/inCode")
    public void inCode(HttpServletResponse httpResponse) throws IOException {
        httpResponse.setContentType("image/jpeg");
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        int labid = labService.findLabById(id).getLabid();
        qrService.inCode(httpResponse.getOutputStream(),labid);
    }

    @RequestMapping("/outCode")
    public void outCode(HttpServletResponse httpServletResponse) throws IOException {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        int labid = labService.findLabById(id).getLabid();
        httpServletResponse.setContentType("image/jpeg");
        qrService.outCode(httpServletResponse.getOutputStream(),labid);
    }

    @GetMapping("/healthAnalyse")
    public ModelAndView healthAnalyse(@RequestParam(defaultValue = "1") int index) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Lab lab = labService.findLabById(authentication.getName());
        PageHelper.startPage(index, 15);
        ModelAndView modelAndView = new ModelAndView();
        PageInfo<HealthInfo> pageInfo = new PageInfo<>(healthInfoService.findExceptionInfoByLabid("2020-04-14", lab.getLabid()));
        modelAndView.addObject("healthAnalyse", healthAnalyseService.healthAnalyseByLabid(lab.getLabid()));
        List<HealthInfo> list = pageInfo.getList();
        modelAndView.addObject("exceptionInfo", list);
        modelAndView.addObject("pre", pageInfo.getPrePage());
        modelAndView.addObject("current", pageInfo.getPageNum());
        modelAndView.addObject("total", pageInfo.getPages());
        modelAndView.addObject("next", pageInfo.getNextPage());
        modelAndView.setViewName("teacher/health_analyse");
        modelAndView.addObject("offline", studentService.findStudentsOffline(lab.getLabid(), "2020-04-14"));
        modelAndView.addObject("online", studentService.findStudentsOnline(lab.getLabid(), "2020-04-14"));
        return modelAndView;
    }

    @PostMapping("/marked")
    @ResponseBody
    public List<StudentInfo> markedStudent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Lab lab = labService.findLabById(authentication.getName());
        return studentService.findMarkedByLabid(lab.getLabid());

    }

    @GetMapping("/markDetail")
    public ModelAndView markDetail(@RequestParam String id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("teacher/mark_detail");
        String name = studentService.findStudentById(id).getName();
        modelAndView.addObject("info", "学号:" + id + " 姓名:" + name);
        List<HealthInfo> history = healthInfoService.findHistory(id);
        history.forEach(h -> {
            h.setShowInTime(dateTimeFormatter.format(h.getInTime()));
            if (h.getOutTime() == null) {
                h.setShowOutTime("没有登记");
            } else {
                h.setShowOutTime(dateTimeFormatter.format(h.getOutTime()));
            }
        });
        modelAndView.addObject("history", history);
        return modelAndView;
    }

}
