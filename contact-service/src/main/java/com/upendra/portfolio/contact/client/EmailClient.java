package com.upendra.portfolio.contact.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.upendra.portfolio.common.dto.ApiResponse;
import com.upendra.portfolio.common.dto.email.EmailRequest;
import com.upendra.portfolio.common.dto.email.EmailResponse;


@FeignClient(name = "email-service",
				fallback=EmailClientFallback.class
)
public interface EmailClient {

    @PostMapping("/api/v1/email/send")
    ApiResponse<EmailResponse> sendEmail(
            @RequestBody EmailRequest request);

}