package com.upendra.portfolio.port.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI portfolioOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Portfolio Service API")
                        .description("REST APIs for Portfolio Platform")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Upendra Dudekonda")
                                .email("dudekondaupendra@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")));
    }
}