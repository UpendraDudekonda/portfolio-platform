package com.upendra.portfolio.auth.service;

import org.springframework.http.ResponseEntity;

import com.upendra.portfolio.auth.dto.request.LoginRequest;
import com.upendra.portfolio.auth.dto.request.RegisterRequest;
import com.upendra.portfolio.auth.dto.response.AuthResult;
import com.upendra.portfolio.auth.dto.response.UserResponse;
import com.upendra.portfolio.common.dto.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

	ApiResponse<UserResponse> register(RegisterRequest request);
	
	AuthResult login(LoginRequest request);
	
	AuthResult refreshToken(String refreshToken);
}
