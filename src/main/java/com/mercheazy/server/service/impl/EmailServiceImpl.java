package com.mercheazy.server.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements com.mercheazy.server.service.EmailService {
    private final JavaMailSender javaMailSender;

    @Value("${spring.app.base-url}")
    private String baseUrl;

    @Override
    public void sendUserVerificationMail(String email, String token) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(email);
            mailMessage.setSubject("Welcome to MerchEazy: Activate your account!");
            mailMessage.setText("To confirm your account, please click here : "
                    + baseUrl + "/api/auth/verify-email?token=" + token);
            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            System.out.println("Error sending email: " + e.getMessage());
        }
    }
}
