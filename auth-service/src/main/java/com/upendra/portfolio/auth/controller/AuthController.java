package com.upendra.portfolio.auth.controller;


import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upendra.portfolio.auth.dto.request.LoginRequest;
import com.upendra.portfolio.auth.dto.request.RegisterRequest;
import com.upendra.portfolio.auth.dto.response.AuthResult;
import com.upendra.portfolio.auth.dto.response.LoginResponse;
import com.upendra.portfolio.auth.dto.response.UserResponse;
import com.upendra.portfolio.auth.repository.UserRepository;
import com.upendra.portfolio.auth.service.AuthService;
import com.upendra.portfolio.auth.service.CookieService;
import com.upendra.portfolio.common.dto.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	private final UserRepository repo;
	private final AuthService authService;
	
//	private final JwtService jwtService;
	
	private final CookieService cookieService;

	public AuthController(AuthService authService,UserRepository repo,CookieService cookieService) {
		
		this.authService = authService;
		this.repo = repo;
		this.cookieService = cookieService;
	}
	
	@PostMapping("/register")
	public ApiResponse<UserResponse> register(@Valid @RequestBody RegisterRequest request){
		
		return	authService.register(request);
	}
	
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<LoginResponse>> login(
	        @Valid @RequestBody LoginRequest request) {

	    AuthResult result = authService.login(request);

	    ResponseCookie access =
	            cookieService.createAccessTokenCookie(result.getAccessToken());

	    ResponseCookie refresh =
	            cookieService.createRefreshTokenCookie(result.getRefreshToken());

	    UserResponse userResponse = UserResponse.builder()
	            .uuid(result.getUser().getUuid())
	            .firstName(result.getUser().getFirstName())
	            .lastName(result.getUser().getLastName())
	            .email(result.getUser().getEmail())
	            .role(result.getUser().getRole())
	            .build();

	    LoginResponse loginResponse = LoginResponse.builder()
	            .user(userResponse)
	            .build();

	    ApiResponse<LoginResponse> response =
	            ApiResponse.<LoginResponse>builder()
	                    .success(true)
	                    .message("Login successful")
	                    .data(loginResponse)
	                    .build();

	    return ResponseEntity.ok()
	            .header(HttpHeaders.SET_COOKIE, access.toString())
	            .header(HttpHeaders.SET_COOKIE, refresh.toString())
	            .body(response);
	}
	
	
	@PostMapping("/refresh")
	public ResponseEntity<ApiResponse<Void>> refreshToken(
	        HttpServletRequest request) {
		
		String refreshToken = cookieService.getRefreshToken(request);

	    AuthResult result = authService.refreshToken(refreshToken);

	    ResponseCookie accessCookie =
	            cookieService.createAccessTokenCookie(result.getAccessToken());

	    return ResponseEntity.ok()
	            .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
	            .body(
	                    ApiResponse.<Void>builder()
	                            .success(true)
	                            .message("Access token refreshed successfully.")
	                            .build()
	            );
	}
	
	@PostMapping("/logout")
	public  ResponseEntity<ApiResponse<Void>> logout() {

	    ResponseCookie access =
	            cookieService.clearAccessTokenCookie();

	    ResponseCookie refresh =
	            cookieService.clearRefreshTokenCookie();

	    return ResponseEntity.ok()
	            .header(HttpHeaders.SET_COOKIE, access.toString())
	            .header(HttpHeaders.SET_COOKIE, refresh.toString())
	            .body( ApiResponse.<Void>builder()
                        .success(true)
                        .message("Logout successful.")
                        .build());
	}
	
//	@GetMapping("/token")
//	public String generateToken() {
//
//	    User user = repo.findByEmail("upendra@gmail.com")
//	            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
//
//	    return jwtService.generateAccessToken(user);
//	}
	
	
	
}
