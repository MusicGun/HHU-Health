package com.test.examine.controller.adminController;


import com.test.examine.entity.Admin;
import com.test.examine.entity.User;
import com.test.examine.service.adminService.AdminService;
import com.test.examine.service.common.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/authority")
public class AuthorityController {

    private AdminService adminService;

    private UserService userService;

    private PasswordEncoder encoder;

    @Autowired
    public AuthorityController(AdminService adminService, UserService userService, PasswordEncoder encoder) {
        this.encoder = encoder;
        this.userService = userService;
        this.adminService = adminService;
    }

    @GetMapping
    public ModelAndView authority() {
        ModelAndView modelAndView = new ModelAndView();
        List<Admin> admins = adminService.findAdmin();
        modelAndView.setViewName("admin/authority");
        modelAndView.addObject("admins", admins);
        return modelAndView;
    }

    @GetMapping("/addAdmin")
    public String addAdmin() {
        return "admin/add_admin";
    }

    @PostMapping("/updateAuthority")
    public String updateAuthority(@RequestParam("id") String id, @RequestParam("p") String p) {
        if (adminService.updateAuthority(id, p) == 0) {
            return "error/500";
        }
        return "redirect:/authority";
    }

    @PostMapping("/deleteAdmin")
    @ResponseBody
    public String deleteAdmin(@RequestBody String id) {
        String self = SecurityContextHolder.getContext().getAuthentication().getName();
        if (self.equals(id)) {
            return "您无法删除您自己";
        }
        int flag = userService.deleteAdmin(id);
        return "success";
    }

    @PostMapping("/addAdmin")
    @ResponseBody
    public String addAdmin(@RequestBody Admin admin) {
        admin.setPassword(encoder.encode(admin.getPassword()));
        admin.setEnable(1);
        if (userService.checkUser(admin.getId())) {
            return "该账户已存在";
        } else {
            if (adminService.checkEmail(admin.getEmail())) {
                return "该邮箱已被注册";
            } else {
                userService.addUser(admin);
            }
        }
        adminService.addAdmin(admin);
        return "success";
    }

}
