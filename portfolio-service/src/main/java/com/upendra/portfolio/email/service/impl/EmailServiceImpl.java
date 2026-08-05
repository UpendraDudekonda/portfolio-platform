package com.upendra.portfolio.email.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.upendra.portfolio.common.dto.email.EmailRequest;
import com.upendra.portfolio.common.dto.email.EmailResponse;
import com.upendra.portfolio.email.service.EmailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final RestClient brevoRestClient;

    @Value("${brevo.api.key}")
    private String brevoApiKey;

    @Value("${brevo.sender.email}")
    private String senderEmail;

    @Value("${brevo.sender.name}")
    private String senderName;

    @Override
    public EmailResponse sendEmail(EmailRequest request) {

        Map<String, Object> body = Map.of(
                "sender", Map.of(
                        "name", senderName,
                        "email", senderEmail
                ),
                "to", new Object[]{
                        Map.of(
                                "email", request.getTo()
                        )
                },
                "subject", request.getSubject(),
                "textContent", request.getBody()
        );

        brevoRestClient.post()
                .uri("/smtp/email")
                .header("api-key", brevoApiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .toBodilessEntity();

        return EmailResponse.builder()
                .to(request.getTo())
                .subject(request.getSubject())
                .status("Email sent successfully.")
                .build();
    }
}