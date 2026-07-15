package com.upendra.portfolio.gateway.util;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class PublicRouteValidator {
	
	private static final List<String> PUBLIC_ENDPOINTS = List.of(
            "/api/v1/auth/register",
            "/api/v1/auth/login",
            "/api/v1/auth/refresh"
    );

    public boolean isPublic(String path) {

        return PUBLIC_ENDPOINTS.stream()
                .anyMatch(path::startsWith);
    }

}
