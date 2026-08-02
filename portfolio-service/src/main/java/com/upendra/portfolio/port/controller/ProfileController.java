package com.upendra.portfolio.port.controller;

import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.upendra.portfolio.auth.security.SecurityUtils;
import com.upendra.portfolio.common.dto.ApiResponse;
import com.upendra.portfolio.port.dto.request.CreateProfileRequest;
import com.upendra.portfolio.port.dto.request.UpdateProfileRequest;
import com.upendra.portfolio.port.dto.response.ProfileResponse;
import com.upendra.portfolio.port.service.ProfileService;

import jakarta.validation.Valid;




@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
	
	private ProfileService profileService; 
	
	public ProfileController(ProfileService profileService) {
		
		this.profileService = profileService;
	}
	
	@PostMapping
	public  ApiResponse<ProfileResponse > createProfile( 
			 												@Valid @RequestBody CreateProfileRequest request){
		
		UUID userUuid = SecurityUtils.getCurrentUserUuid();
		ProfileResponse profileResponse = profileService.createProfile(userUuid, request);
		
		return ApiResponse.<ProfileResponse>builder()
				.success(true)
				.message("Profile created successfully.")
				.data(profileResponse)
				.build();
	}
	
	@GetMapping("/me")
	public ApiResponse<ProfileResponse> getProfile(){
		
		UUID userUuid = SecurityUtils.getCurrentUserUuid();
		ProfileResponse profileResponse = profileService.getMyProfile(userUuid);
		
		return ApiResponse.<ProfileResponse>builder()
				.success(true)
				.message("Profile fetched successfully.")
				.data(profileResponse)
				.build();
	}
	
	@PutMapping
	public  ApiResponse<ProfileResponse > updateProfile( 
															@Valid @RequestBody UpdateProfileRequest request){
		UUID userUuid = SecurityUtils.getCurrentUserUuid();
		ProfileResponse profileResponse = profileService.updateProfile(userUuid, request);
		
		return ApiResponse.<ProfileResponse>builder()
				.success(true)
				.message("Profile updated successfully.")
				.data(profileResponse)
				.build();
	}
	
	@DeleteMapping
	public ApiResponse<Void> deleteProfile(
	        ) {

		UUID userUuid = SecurityUtils.getCurrentUserUuid();
	    profileService.deleteProfile(userUuid);

	    return ApiResponse.<Void>builder()
	            .success(true)
	            .message("Profile deleted successfully.")
	            .build();
	}
	
	@PostMapping(
	        value="/image",
	        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
	)
	public ApiResponse<ProfileResponse> uploadProfileImage(
	       
	        @RequestPart MultipartFile file
	) {
		
		UUID userUuid = SecurityUtils.getCurrentUserUuid();
	    ProfileResponse res = profileService.uploadProfileImage(
	    		userUuid,
	            file
	    );

	    return ApiResponse.<ProfileResponse>builder()
	            .success(true)
	            .message("Success")
	            .data(res)
	            .build();
	}
	
	
	@PostMapping(
	        value = "/resume",
	        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
	)
	public ApiResponse<ProfileResponse> uploadResume(

	       
	        

	        @RequestPart("file")
	        MultipartFile file) {
		
		UUID userUuid = SecurityUtils.getCurrentUserUuid();
	    return ApiResponse.<ProfileResponse>builder()
	            .success(true)
	            .message("Resume uploaded successfully.")
	            .data(
	                    profileService.uploadResume(
	                    		userUuid,
	                            file
	                    )
	            )
	            .build();
	}
	

}



	
