package com.upendra.portfolio.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upendra.portfolio.auth.dto.request.RegisterRequest;
import com.upendra.portfolio.auth.dto.response.UserResponse;
import com.upendra.portfolio.auth.service.AuthService;
import com.upendra.portfolio.common.dto.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		
		this.authService = authService;
	}
	
	@PostMapping("/register")
	public ApiResponse<UserResponse> register(@Valid @RequestBody RegisterRequest request){
		
		return	authService.register(request);
	
	}
	
	
}
