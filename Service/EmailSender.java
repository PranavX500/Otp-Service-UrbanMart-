package com.example.OTP_Service.Service;

import com.example.OTP_Service.DTO.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailSender {

    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String email, String subject, String text) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject(subject);
        msg.setText(text);

        mailSender.send(msg);
    }
}




