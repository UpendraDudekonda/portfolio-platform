package com.upendra.portfolio.email.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
@Configuration
public class BrevoConfig {

    @Value("${brevo.api.url}")
    private String brevoApiUrl;

    @Bean
    public RestClient brevoRestClient() {

        return RestClient.builder()
                .baseUrl(brevoApiUrl)
                .build();
    }
}