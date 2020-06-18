package com.test.examine.controller.adminController;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.test.examine.entity.HealthInfo;

import com.test.examine.entity.StudentInfo;
import com.test.examine.service.adminService.HealthAnalyseService;
import com.test.examine.service.common.HealthInfoService;
import com.test.examine.service.studentService.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/healthAnalyse")
public class HealthAnalyseController {


    private HealthAnalyseService healthAnalyseService;


    private HealthInfoService healthInfoService;

    private StudentService studentService;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    @Autowired
    private HealthAnalyseController(HealthAnalyseService healthAnalyseService, HealthInfoService healthInfoService, StudentService studentService) {
        this.healthAnalyseService = healthAnalyseService;
        this.healthInfoService = healthInfoService;
        this.studentService = studentService;
    }

    @GetMapping
    public ModelAndView healthAnalyse(@RequestParam(defaultValue = "1") int index) {
        PageHelper.startPage(index, 15);
        ModelAndView modelAndView = new ModelAndView();
        PageInfo<HealthInfo> pageInfo = new PageInfo<>(healthInfoService.findExceptionInfo("2020-04-24"));
        modelAndView.addObject("healthAnalyse", healthAnalyseService.healthAanlyse());
        List<HealthInfo> list = pageInfo.getList();
        modelAndView.addObject("exceptionInfo", list);
        modelAndView.addObject("pre", pageInfo.getPrePage());
        modelAndView.addObject("current", pageInfo.getPageNum());
        modelAndView.addObject("total", pageInfo.getPages());
        modelAndView.addObject("next", pageInfo.getNextPage());
        modelAndView.setViewName("admin/health_analyse");
        modelAndView.addObject("map", healthAnalyseService.findOnlineByTime("2020-04-24"));
        return modelAndView;
    }


    @PostMapping("/mark")
    @ResponseBody
    public int mark(@RequestBody Map<String, Integer> data) {
        String id = data.get("id") + "";
        Integer flag = data.get("flag");
        if (flag == 1) {
            studentService.markStudent(id);
        } else {
            studentService.unmarkStudent(id);
        }
        return 0;
    }

    @PostMapping("/marked")
    @ResponseBody
    public List<StudentInfo> markedStudent() {
        return studentService.findMarked();
    }


    @GetMapping("/markDetail")
    public ModelAndView markDetail(@RequestParam String id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/mark_detail");
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

    @PostMapping("/healthInfoDetail")
    public ModelAndView healthInfo(@RequestParam("labid") int labid) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("online", studentService.findStudentsOnline(labid, "2020-04-14"));
        modelAndView.addObject("offline", studentService.findStudentsOffline(labid, "2020-04-14"));
        modelAndView.setViewName("admin/health_info_detail");
        return modelAndView;

    }
}
