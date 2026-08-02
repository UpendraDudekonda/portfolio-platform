package com.upendra.portfolio.auth.service;

import org.springframework.http.ResponseCookie;

import jakarta.servlet.http.HttpServletRequest;

public interface CookieService {
	
	ResponseCookie createAccessTokenCookie(String token);

    ResponseCookie createRefreshTokenCookie(String token);

    ResponseCookie clearAccessTokenCookie();

    ResponseCookie clearRefreshTokenCookie();
    
    String getAccessToken(HttpServletRequest request);

    String getRefreshToken(HttpServletRequest request);

}
