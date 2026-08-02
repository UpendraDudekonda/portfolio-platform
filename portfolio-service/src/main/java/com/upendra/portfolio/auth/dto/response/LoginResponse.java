package com.upendra.portfolio.auth.dto.response;

import java.util.UUID;

import com.upendra.portfolio.auth.enums.Role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
	
	private UserResponse user;

   

}
