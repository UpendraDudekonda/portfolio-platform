package com.upendra.portfolio.auth.service.impl;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import com.upendra.portfolio.auth.config.JwtProperties;
import com.upendra.portfolio.auth.service.CookieService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CookieServiceImpl implements CookieService {
	
	private final JwtProperties jwtProperties;
	
	private static final String ACCESS_TOKEN = "access_token";
	private static final String REFRESH_TOKEN = "refresh_token";

	@Override
	public ResponseCookie createAccessTokenCookie(String token) {
		
		 return ResponseCookie.from(ACCESS_TOKEN, token)
		            .httpOnly(true)
		            .secure(false)
		            .path("/")
		            .maxAge(jwtProperties.getAccessTokenExpiration() / 1000)
		            .sameSite("Strict")
		            .build();
	}

	@Override
	public ResponseCookie createRefreshTokenCookie(String token) {
		
		 return ResponseCookie.from(REFRESH_TOKEN, token)
		            .httpOnly(true)
		            .secure(false)
		            .path("/")
		            .maxAge(jwtProperties.getRefreshTokenExpiration() / 1000)
		            .sameSite("Strict")
		            .build();
	}
	
	@Override
	public String getAccessToken(HttpServletRequest request) {
	    return getCookieValue(request, ACCESS_TOKEN);
	}

	@Override
	public String getRefreshToken(HttpServletRequest request) {
	    return getCookieValue(request, REFRESH_TOKEN);
	}
	
	
	//Helper method
	private String getCookieValue(HttpServletRequest request, String cookieName) {

	    if (request.getCookies() == null) {
	        return null;
	    }

	    for (Cookie cookie : request.getCookies()) {
	        if (cookieName.equals(cookie.getName())) {
	            return cookie.getValue();
	        }
	    }

	    return null;
	}
	
	

	@Override
	public ResponseCookie clearAccessTokenCookie() {
		
		return ResponseCookie.from(ACCESS_TOKEN, "")
	            .httpOnly(true)
	            .secure(false)
	            .path("/")
	            .maxAge(0)
	            .sameSite("Strict")
	            .build();
	}

	@Override
	public ResponseCookie clearRefreshTokenCookie() {
		
		 return ResponseCookie.from(REFRESH_TOKEN, "")
		            .httpOnly(true)
		            .secure(false)
		            .path("/")
		            .maxAge(0)
		            .sameSite("Strict")
		            .build();
	}

}
