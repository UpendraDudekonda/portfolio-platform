package com.upendra.portfolio.auth.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upendra.portfolio.auth.dto.request.RegisterRequest;
import com.upendra.portfolio.auth.dto.response.UserResponse;
import com.upendra.portfolio.auth.entity.User;
import com.upendra.portfolio.auth.repository.UserRepository;
import com.upendra.portfolio.auth.service.AuthService;
import com.upendra.portfolio.common.dto.ApiResponse;



@Service
public class AuthServiceImpl implements AuthService {
	
	private final UserRepository userRepository ; 
	private final PasswordEncoder passwordEncoder;
	
	

	public AuthServiceImpl(UserRepository userRepository,
			PasswordEncoder passwordEncoder) {
		
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}



	@Override
	@Transactional
	public ApiResponse<UserResponse> register(RegisterRequest request) {
		
		if(userRepository.existsByEmail(request.getEmail())) {
			throw new RuntimeException("Email alread exist.");
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
				.build();
		
		return ApiResponse.<UserResponse>builder()
				.success(true)
				.message("user registered successfully")
				.data(response)
				.build();
	}

}
