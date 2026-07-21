package com.upendra.portfolio.port.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.upendra.portfolio.port.dto.response.ContactStatsResponse;


@FeignClient(name = "CONTACT-SERVICE",
			fallback = ContactClientFallback.class
)
public interface ContactClient {


    @GetMapping("/api/v1/contact/internal/stats")
    ContactStatsResponse getContactStats();

}