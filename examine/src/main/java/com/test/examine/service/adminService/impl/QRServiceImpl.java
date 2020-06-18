package com.test.examine.service.adminService.impl;

import com.google.zxing.qrcode.encoder.QRCode;
import com.test.examine.service.adminService.QRService;
import io.nayuki.qrcodegen.QrCode;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;


@Service
public class QRServiceImpl implements QRService {


    @Override
    public void inCode(OutputStream outputStream,int labid) {
        QrCode qrCode = QrCode.encodeText("http://101.200.191.33:8080/", QrCode.Ecc.HIGH);
        BufferedImage bufferedImage = qrCode.toImage(5, 20);
        try {
            ImageIO.write(bufferedImage, "jpg", outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void outCode(OutputStream outputStream,int labid) {
        QrCode qrCode = QrCode.encodeText("http://101.200.191.33:8080/health/out?labid="+labid, QrCode.Ecc.HIGH);
        BufferedImage bufferedImage = qrCode.toImage(5, 20);
        try {
            ImageIO.write(bufferedImage, "jpg", outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
