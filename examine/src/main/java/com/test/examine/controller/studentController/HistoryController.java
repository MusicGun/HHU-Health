package com.test.examine.controller.studentController;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.test.examine.entity.HealthInfo;
import com.test.examine.service.common.HealthInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/history")
public class HistoryController {

    private HealthInfoService healthInfoService;

    @Autowired
    public HistoryController(HealthInfoService healthInfoService) {
        this.healthInfoService = healthInfoService;
    }

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    @GetMapping
    public ModelAndView history(@RequestParam(defaultValue = "1") int index) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        ModelAndView modelAndView = new ModelAndView();
        PageHelper.startPage(index,14);
        PageInfo<HealthInfo> pageInfo = new PageInfo<>(healthInfoService.findHistory(id));
        List<HealthInfo> history = pageInfo.getList();
        history.forEach(h -> {
            h.setShowInTime(dateTimeFormatter.format(h.getInTime()));
            if (h.getOutTime() == null) {
                h.setShowOutTime("没有登记");
            } else {
                h.setShowOutTime(dateTimeFormatter.format(h.getOutTime()));
            }
        });
        modelAndView.addObject("current",pageInfo.getPageNum());
        modelAndView.addObject("pre",pageInfo.getPrePage());
        modelAndView.addObject("total",pageInfo.getPages());
        modelAndView.addObject("next",pageInfo.getNextPage());
        modelAndView.addObject("history", history);
        modelAndView.setViewName("student/history");
        return modelAndView;
    }
}
