package com.upendra.portfolio.auth.service;

import com.upendra.portfolio.auth.dto.request.RegisterRequest;
import com.upendra.portfolio.auth.dto.response.UserResponse;
import com.upendra.portfolio.common.dto.ApiResponse;

public interface AuthService {

	ApiResponse<UserResponse> register(RegisterRequest request);
}
