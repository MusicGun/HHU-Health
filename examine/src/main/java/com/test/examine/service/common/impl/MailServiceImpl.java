package com.test.examine.service.common.impl;


import com.test.examine.service.common.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {

    private JavaMailSender javaMailSender;

    @Autowired
    public MailServiceImpl(JavaMailSender javaMailSender)
    {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendMail(String toMail) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = null;
        try {
            messageHelper = new MimeMessageHelper(message, true, "utf-8");
            messageHelper.setFrom("邮箱");
            messageHelper.setTo(toMail);
            messageHelper.setSubject("HHUHealth");
            messageHelper.setText("msg", true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendException(String info, String receiver) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = null;
        try {
            messageHelper = new MimeMessageHelper(message, true, "utf-8");
            messageHelper.setFrom("邮箱");
            messageHelper.setTo(receiver);
            messageHelper.setSubject("河海健康系统");
            messageHelper.setText(""+info+""
                    + "", true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
