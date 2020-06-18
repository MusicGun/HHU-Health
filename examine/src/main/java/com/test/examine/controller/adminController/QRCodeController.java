package com.test.examine.controller.adminController;



import com.test.examine.service.adminService.LabService;
import com.test.examine.service.adminService.QRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/QRCode")
public class QRCodeController {

    private QRService qrService;

    private LabService labService;
    @Autowired
    public QRCodeController(QRService qrService,LabService labService) {

        this.qrService = qrService;
        this.labService = labService;
    }
    @GetMapping
    public ModelAndView  qr() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("labs",labService.findLabs());
        modelAndView.setViewName("admin/QRCode");
        return modelAndView;
    }

    @PostMapping
    public ModelAndView qrp(@RequestParam int labid) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("admin/Code");
        modelAndView.addObject("labid",labid);
        return modelAndView;
    }

    @RequestMapping("/inCode")
    public void inCode(@RequestParam("labid")int labid, HttpServletResponse httpResponse) throws IOException {
        httpResponse.setContentType("image/jpeg");
        qrService.inCode(httpResponse.getOutputStream(),labid);
    }

    @RequestMapping("/outCode")
    public void outCode(@RequestParam("labid")int labid, HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setContentType("image/jpeg");
        qrService.outCode(httpServletResponse.getOutputStream(),labid);
    }


}
