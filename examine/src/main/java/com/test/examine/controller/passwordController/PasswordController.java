package com.test.examine.controller.passwordController;


import com.test.examine.service.common.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/forgot")
public class PasswordController {


    private UserService userService;
    @Autowired
    public PasswordController(UserService userService)
    {
        this.userService = userService;
    }



    @PostMapping
    @ResponseBody
    public String checkUser(@RequestBody String username)
    {
        boolean flag = userService.checkUser(username);
        return "页面还在开发中...";
    }
}
