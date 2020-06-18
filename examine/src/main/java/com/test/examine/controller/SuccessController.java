package com.test.examine.controller;


import org.springframework.security.core.Authentication;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/tmp")
public class SuccessController {

    @GetMapping
    public String redirect() {
        return "redirect:index";
    }

    @PostMapping
    public String postRedirect(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String a = authentication.getAuthorities().toArray()[0].toString();
        if (a.equals("p1")||a.equals("p2")) {
            return "redirect:admin";
        } else if(a.equals("p3"))
        {
            return "redirect:teacher";
        }
        else{
            return "redirect:index";
        }
    }

}
