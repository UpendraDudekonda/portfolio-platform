package com.upendra.portfolio.email.service;

import com.upendra.portfolio.common.dto.email.EmailRequest;
import com.upendra.portfolio.common.dto.email.EmailResponse;

public interface EmailService {

    EmailResponse sendEmail(EmailRequest request);

}