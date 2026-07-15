package com.upendra.portfolio.gateway.service;

import java.util.UUID;

import io.jsonwebtoken.Claims;

public interface JwtService {
	
	Claims extractAllClaims(String token);

    String extractEmail(String token);

    UUID extractUuid(String token);

    String extractRole(String token);

    boolean isTokenValid(String token);

}
