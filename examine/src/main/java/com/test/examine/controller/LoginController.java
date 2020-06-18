package com.test.examine.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class LoginController {


    private AtomicInteger count;

    public LoginController() {
        this.count = new AtomicInteger(0);
    }

    @GetMapping("/login")
    public String login() {
        return "login-view";
    }

    @PostMapping("/loginFailure")
    public ModelAndView loginFailure() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("msg", "用户名或密码错误");
        modelAndView.setViewName("login-view");
        return modelAndView;
    }


    @RequestMapping("/getCount")
    @ResponseBody
    public int count() {
        return count.incrementAndGet();
    }
}
