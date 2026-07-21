package com.upendra.portfolio.port.service;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.upendra.portfolio.port.dto.request.CreateProfileRequest;
import com.upendra.portfolio.port.dto.request.UpdateProfileRequest;
import com.upendra.portfolio.port.dto.response.ProfileResponse;

public interface ProfileService {

	 ProfileResponse createProfile(UUID userUuid, CreateProfileRequest request);

	    ProfileResponse getMyProfile(UUID userUuid);

	    ProfileResponse updateProfile(UUID userUuid, UpdateProfileRequest request);

	    void deleteProfile(UUID userUuid);
	    
	    ProfileResponse uploadProfileImage(
	            UUID userUuid,
	            MultipartFile file
	    );
	    
	    ProfileResponse uploadResume(
	            UUID userUuid,
	            MultipartFile file
	    );
}
