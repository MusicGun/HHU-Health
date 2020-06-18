package com.test.examine.controller.adminController;


import com.test.examine.entity.Admin;
import com.test.examine.entity.Lab;
import com.test.examine.service.adminService.AdminService;
import com.test.examine.service.adminService.LabService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/labManage")
public class LabController {


    private LabService labService;


    private AdminService adminService;

    @Autowired
    public LabController(LabService labService, AdminService adminService) {
        this.labService = labService;
        this.adminService = adminService;
    }

    @GetMapping
    public ModelAndView labManage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("labs", labService.findLabs());
        modelAndView.setViewName("admin/lab_manage");
        return modelAndView;
    }

    @PostMapping("/updateLabAdmin")
    @ResponseBody
    public String updateAdmin(@RequestBody Map<String, Integer> data) {
        String id = data.get("id") + "";
        Integer labid = (data.get("labid"));
        Admin admin = adminService.findAdminById(id);
        if (admin == null) {
            return "不存在该管理员";
        }

        if (admin.getRole().equals("teacher")) {
            Lab lab = labService.findLabById(id);
            if (lab != null) {
                return "一个教师至多管理一个实验室";
            } else {
                labService.updateLabAdmin(labid, id);
                return "success";
            }
        } else {
            labService.updateLabAdmin(labid, id);
            return "success";
        }
    }

    @PostMapping("/addTotal")
    public String addTotal(@RequestParam("labid") int labid, @RequestParam("n") int n) {
        if (labService.addTotal(labid, n) == 0) {
            return "error/500";
        }
        return "redirect:/labManage";
    }
}
