package com.upendra.portfolio.email.service.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.upendra.portfolio.common.dto.email.EmailRequest;
import com.upendra.portfolio.common.dto.email.EmailResponse;
import com.upendra.portfolio.email.service.EmailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public EmailResponse sendEmail(EmailRequest request) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(request.getTo());
        message.setSubject(request.getSubject());
        message.setText(request.getBody());

        mailSender.send(message);

        return EmailResponse.builder()
                .to(request.getTo())
                .subject(request.getSubject())
                .status("Email sent successfully.")
                .build();
    }
}