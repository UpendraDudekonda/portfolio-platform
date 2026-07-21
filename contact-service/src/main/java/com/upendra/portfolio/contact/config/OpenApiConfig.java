package com.upendra.portfolio.contact.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI contactOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Contact Service API")
                        .version("v1.0")
                        .description("Contact APIs for Portfolio Platform")
                        .contact(new Contact()
                                .name("Upendra Dudekonda")
                                .email("dudekondaupendra@gmail.com"))
                        .license(new License().name("Apache 2.0")));
    }
}