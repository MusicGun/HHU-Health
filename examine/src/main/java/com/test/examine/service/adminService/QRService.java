package com.test.examine.service.adminService;


import java.io.OutputStream;

public interface QRService {



    void inCode(OutputStream outputStream,int labid);


    void outCode(OutputStream outputStream,int labid);


}
