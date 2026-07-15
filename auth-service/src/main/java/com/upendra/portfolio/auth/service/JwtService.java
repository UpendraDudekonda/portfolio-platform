package com.upendra.portfolio.auth.service;

import com.upendra.portfolio.auth.entity.User;

public interface JwtService {
	
	String generateAccessToken(User user);

    String generateRefreshToken(User user);

    String extractEmail(String token);

    String extractRole(String token);

    boolean isTokenValid(String token, User user);

    boolean isTokenExpired(String token);
    
    boolean isRefreshTokenValid(String token, User user);

}
