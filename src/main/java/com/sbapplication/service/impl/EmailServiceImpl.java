package com.sbapplication.service.impl;

import com.sbapplication.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String to, String msg, String subject) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(to);
            mail.setText(msg);
            mail.setSubject(subject);
            mail.setCc("sandeepkumar201198@gmail.com");
            mail.setBcc("lila.devi99165@gmail.com");
            javaMailSender.send(mail);
            log.info("Mail sent successfully");
        }
        catch(Exception e){
            log.error("Something went wrong, exception occured ", e);
        }
    }
}
