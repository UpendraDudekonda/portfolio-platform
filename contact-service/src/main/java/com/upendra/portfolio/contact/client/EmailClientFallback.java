package com.upendra.portfolio.contact.client;

import org.springframework.stereotype.Component;

import com.upendra.portfolio.common.dto.ApiResponse;
import com.upendra.portfolio.common.dto.email.EmailRequest;
import com.upendra.portfolio.common.dto.email.EmailResponse;

@Component
public class EmailClientFallback 
        implements EmailClient {


	@Override
	public ApiResponse<EmailResponse> sendEmail(
												EmailRequest request) {


		return ApiResponse.<EmailResponse>builder()
            .success(false)
            .message("Email service unavailable")
            .build();
	}

}
