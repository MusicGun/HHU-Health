package com.test.examine.controller.adminController;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/updateInfo")
public class InfoController {




    @GetMapping
    public ModelAndView updateInfo()
    {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("admin/updateInfo");

        return modelAndView;

    }


}
