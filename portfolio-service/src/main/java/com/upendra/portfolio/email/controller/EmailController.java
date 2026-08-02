package com.upendra.portfolio.email.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upendra.portfolio.common.dto.ApiResponse;
import com.upendra.portfolio.common.dto.email.EmailRequest;
import com.upendra.portfolio.common.dto.email.EmailResponse;
import com.upendra.portfolio.email.service.EmailService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public ApiResponse<EmailResponse> sendEmail(
            @Valid @RequestBody EmailRequest request) {

        return ApiResponse.<EmailResponse>builder()
                .success(true)
                .message("Email sent successfully.")
                .data(emailService.sendEmail(request))
                .build();
    }
}