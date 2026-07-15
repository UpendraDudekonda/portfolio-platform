package com.upendra.portfolio.auth.dto.response;

import com.upendra.portfolio.auth.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResult {
	
	private User user;

    private String accessToken;

    private String refreshToken;

}
