package com.upendra.portfolio.auth.service.impl;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upendra.portfolio.auth.dto.request.LoginRequest;
import com.upendra.portfolio.auth.dto.request.RegisterRequest;
import com.upendra.portfolio.auth.dto.response.AuthResult;
import com.upendra.portfolio.auth.dto.response.LoginResponse;
import com.upendra.portfolio.auth.dto.response.UserResponse;
import com.upendra.portfolio.auth.entity.User;
import com.upendra.portfolio.auth.enums.AccountStatus;
import com.upendra.portfolio.auth.repository.UserRepository;
import com.upendra.portfolio.auth.service.AuthService;
import com.upendra.portfolio.auth.service.JwtService;
import com.upendra.portfolio.common.dto.ApiResponse;
import com.upendra.portfolio.common.exception.DuplicateResourceException;
import com.upendra.portfolio.common.exception.ResourceNotFoundException;
import com.upendra.portfolio.common.exception.UnauthorizedException;

import jakarta.servlet.http.HttpServletRequest;



@Service
public class AuthServiceImpl implements AuthService {
	
	private final UserRepository userRepository ; 
	private final PasswordEncoder passwordEncoder;
	
	private final JwtService jwtService;
	
	

	public AuthServiceImpl(UserRepository userRepository,
			PasswordEncoder passwordEncoder,JwtService jwtService) {
		
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}



	@Override
	@Transactional
	public ApiResponse<UserResponse> register(RegisterRequest request) {
		
		if(userRepository.existsByEmail(request.getEmail())) {
			throw new DuplicateResourceException("Email is already registered.");
		}
		
		User user = new User();
		
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setEmail(request.getEmail());
		user.setPassword(
				passwordEncoder.encode(request.getPassword()) 
				);
		
		User savedUser = userRepository.save(user);
		
		UserResponse response = UserResponse.builder()
				.uuid(savedUser.getUuid()) 
				.firstName(savedUser.getFirstName())
				.lastName(savedUser.getLastName())
				.email(savedUser.getEmail())
				.role(savedUser.getRole())
				.build();
		
		return ApiResponse.<UserResponse>builder()
				.success(true)
				.message("user registered successfully")
				.data(response)
				.build();
	}


	// login method
	@Override
	public AuthResult login(LoginRequest request) {
		
		//verify if he already registered before login
		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> 
				 new ResourceNotFoundException("User not found"));
		
		//verify the correct credentials
		if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			 throw new UnauthorizedException("Invalid email or password.");
		}
		
		//Update the last login time:
		user.setLastLogin(LocalDateTime.now());
		userRepository.save(user);
		
		String accessToken = jwtService.generateAccessToken(user);

		String refreshToken = jwtService.generateRefreshToken(user);
		
//		UserResponse userResponse =
//		        UserResponse.builder()
//		                .uuid(user.getUuid())
//		                .firstName(user.getFirstName())
//		                .lastName(user.getLastName())
//		                .email(user.getEmail())
//		                .build();
//		
//		//Build the response:
//		LoginResponse loginResponse = LoginResponse.builder()
//											.user(userResponse)
//											.role(user.getRole())
//											.build();
//		//Final Generic response returning
//		return ApiResponse.<LoginResponse>builder()
//				.success(true)
//				.message("login successfull.")
//				.data(loginResponse)
//				.build();
		
//		AuthResult authResult = AuthResult.builder()
//				.accessToken(accessToken)
//				.refreshToken(refreshToken)
//				.loginResponse(loginResponse)
//				.build();
		
		return AuthResult.builder()
		        .user(user)
		        .accessToken(accessToken)
		        .refreshToken(refreshToken)
		        .build();
	}



	@Override
	public AuthResult refreshToken(String refreshToken) {

		//checking whether the token is exist in request
		if (refreshToken == null || refreshToken.isBlank()) {
		    throw new UnauthorizedException("Refresh token is missing.");
		}
		
		//extract the email , present in the subject of refreshToken(token)
		String email = jwtService.extractEmail(refreshToken);
		
		
		//Finding the user
		User user = userRepository.findByEmail(email).orElseThrow(() ->
				 new UnauthorizedException("User not found."));
		
		//prevents blocked users from continuing to refresh tokens.
		if (user.getAccountStatus() != AccountStatus.ACTIVE) {
		    throw new UnauthorizedException("Account is inactive.");
		}
		
		//Validate the refresh token, This verifies:- Signature ,Expiration,Subject (email)
		if (!jwtService.isRefreshTokenValid(refreshToken, user)) {
		    throw new UnauthorizedException("Invalid refresh token.");
		}
		
		//Generate a new Access Token (but not new refreshToken)
		String newAccessToken = jwtService.generateAccessToken(user);
		
		return AuthResult.builder()
		        .accessToken(newAccessToken)
		        .user(user)
		        .build();
	}



	

	
	
	
}
