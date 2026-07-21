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
	public  ApiResponse<ProfileResponse > createProfile( @RequestHeader("X-User-UUID") String userUuid,
			 												@Valid @RequestBody CreateProfileRequest request){
		
		ProfileResponse profileResponse = profileService.createProfile(UUID.fromString(userUuid), request);
		
		return ApiResponse.<ProfileResponse>builder()
				.success(true)
				.message("Profile created successfully.")
				.data(profileResponse)
				.build();
	}
	
	@GetMapping("/me")
	public ApiResponse<ProfileResponse> getProfile(@RequestHeader("X-User-UUID") String userUuid){
		
		ProfileResponse profileResponse = profileService.getMyProfile(UUID.fromString(userUuid));
		
		return ApiResponse.<ProfileResponse>builder()
				.success(true)
				.message("Profile fetched successfully.")
				.data(profileResponse)
				.build();
	}
	
	@PutMapping
	public  ApiResponse<ProfileResponse > updateProfile( @RequestHeader("X-User-UUID") String userUuid,
															@Valid @RequestBody UpdateProfileRequest request){
		
		ProfileResponse profileResponse = profileService.updateProfile(UUID.fromString(userUuid), request);
		
		return ApiResponse.<ProfileResponse>builder()
				.success(true)
				.message("Profile updated successfully.")
				.data(profileResponse)
				.build();
	}
	
	@DeleteMapping
	public ApiResponse<Void> deleteProfile(
	        @RequestHeader("X-User-UUID") String userUuid) {

	    profileService.deleteProfile(UUID.fromString(userUuid));

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
	        @RequestHeader("X-User-UUID") String userUuid,
	        @RequestPart MultipartFile file
	) {

	    ProfileResponse res = profileService.uploadProfileImage(
	            UUID.fromString(userUuid),
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

	        @RequestHeader("X-User-UUID")
	        String userUuid,

	        @RequestPart("file")
	        MultipartFile file) {

	    return ApiResponse.<ProfileResponse>builder()
	            .success(true)
	            .message("Resume uploaded successfully.")
	            .data(
	                    profileService.uploadResume(
	                            UUID.fromString(userUuid),
	                            file
	                    )
	            )
	            .build();
	}
	

}



	
