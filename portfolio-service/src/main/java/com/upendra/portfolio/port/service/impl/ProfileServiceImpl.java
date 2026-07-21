package com.upendra.portfolio.port.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.upendra.portfolio.common.dto.ApiResponse;
import com.upendra.portfolio.common.exception.DuplicateResourceException;
import com.upendra.portfolio.common.exception.ResourceNotFoundException;
import com.upendra.portfolio.port.client.MediaClient;
import com.upendra.portfolio.port.dto.media.UploadResponse;
import com.upendra.portfolio.port.dto.request.CreateProfileRequest;
import com.upendra.portfolio.port.dto.request.UpdateProfileRequest;
import com.upendra.portfolio.port.dto.response.ProfileResponse;
import com.upendra.portfolio.port.entity.Profile;
import com.upendra.portfolio.port.repository.ProfileRepository;
import com.upendra.portfolio.port.service.ProfileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService{

	private final ProfileRepository profileRepository;
	
	private final MediaClient mediaClient;
	
	
	

	@Override
	public ProfileResponse createProfile(UUID userUuid, CreateProfileRequest request) {

		//Duplicate check
		if (profileRepository.existsByUserUuid(userUuid)) {
		    throw new DuplicateResourceException("Profile already exists.");
		}
		
		Profile profile = Profile.builder()
		        .userUuid(userUuid)
		        .fullName(request.getFullName())
		        .headline(request.getHeadline())
		        .about(request.getAbout())
		        .location(request.getLocation())
		        
		        .profileImageUrl(null)
		        .profileImagePublicId(null)

		        .resumeUrl(null)
		        .resumePublicId(null)
		        
		        .createdAt(LocalDateTime.now())
		        .updatedAt(LocalDateTime.now())
		        .build();
		
		Profile savedProfile =profileRepository.save(profile);
		
//		ProfileResponse profileResponse = ProfileResponse.builder()
//				                          .userUuid(savedProfile.getUserUuid())
//				                          .fullName(savedProfile.getFullName())
//				                          .headline(savedProfile.getHeadline())
//				                          .about(savedProfile.getAbout())
//				                          .location(savedProfile.getLocation())
//				                          .profileImageUrl(savedProfile.getProfileImageUrl())
//				                          .resumeUrl(savedProfile.getResumeUrl())
//				                          .updatedAt(savedProfile.getUpdatedAt())
//				                          .createdAt(savedProfile.getCreatedAt())
//				                          .build();
				                          
		
		return mapToResponse(savedProfile);
	}

	private ProfileResponse mapToResponse(Profile profile) {
	    return ProfileResponse.builder()
	            .id(profile.getId())
	            .userUuid(profile.getUserUuid())
	            .fullName(profile.getFullName())
	            .headline(profile.getHeadline())
	            .about(profile.getAbout())
	            .location(profile.getLocation())
	            .profileImageUrl(profile.getProfileImageUrl())
	         //   .profileImagePublicId(profile.getProfileImagePublicId())
	            .resumeUrl(profile.getResumeUrl())
	         //   .resumePublicId(profile.getResumePublicId())
	            .createdAt(profile.getCreatedAt())
	            .updatedAt(profile.getUpdatedAt())
	            .build();
	}
	
	@Override
	public ProfileResponse getMyProfile(UUID userUuid) {
		
		Profile profile = profileRepository.findByUserUuid(userUuid)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException("Profile not found."));

	    return mapToResponse(profile);
	}

	@Override
	public ProfileResponse updateProfile(UUID userUuid, UpdateProfileRequest request) {

		Profile profile = profileRepository.findByUserUuid(userUuid)
		        .orElseThrow(() ->
		                new ResourceNotFoundException("Profile not found."));
		
		if (request.getFullName() != null) {
		    profile.setFullName(request.getFullName());
		}

		if (request.getHeadline() != null) {
		    profile.setHeadline(request.getHeadline());
		}

		if (request.getAbout() != null) {
		    profile.setAbout(request.getAbout());
		}

		if (request.getLocation() != null) {
		    profile.setLocation(request.getLocation());
		}

		

		profile.setUpdatedAt(LocalDateTime.now());


		Profile saved = profileRepository.save(profile);

		return mapToResponse(saved);
	}

	@Override
	public void deleteProfile(UUID userUuid) {
		
		Profile profile = profileRepository.findByUserUuid(userUuid)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException("Profile not found."));

	    profileRepository.delete(profile);
	}

	
	@Override
	public ProfileResponse uploadProfileImage(UUID userUuid, MultipartFile file) {


		Profile profile =
			    profileRepository.findByUserUuid(userUuid)
			        .orElseThrow(() ->
			        	new ResourceNotFoundException("Profile not found.") );
		
		if (profile.getProfileImagePublicId() != null) {
		    mediaClient.deleteMedia(
		        profile.getProfileImagePublicId(),
		        "image"
		    );
		}
		
		
		//give media-service to upload and get uls and publicId
		ApiResponse<UploadResponse> response =
		        mediaClient.uploadImage(file, "profile");
		
		
		UploadResponse upload = response.getData();
		
		//map to profile to save in db
		profile.setProfileImageUrl(upload.getSecureUrl());

		profile.setProfileImagePublicId(upload.getPublicId());

		profile.setUpdatedAt(LocalDateTime.now());
		
		//save in DB
		profileRepository.save(profile);
		
		return mapToResponse(profile);

	}
	
	@Override
	public ProfileResponse uploadResume(UUID userUuid,
	                                    MultipartFile file) {

	    Profile profile = profileRepository.findByUserUuid(userUuid)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException("Profile not found."));

	    // Delete old resume if present
	    if (profile.getResumePublicId() != null) {

	        mediaClient.deleteMedia(
	                profile.getResumePublicId(),
	                "raw"
	        );
	    }

	    ApiResponse<UploadResponse> response =
	            mediaClient.uploadFile(
	                    file,
	                    "resume"
	            );

	    UploadResponse upload = response.getData();

	    profile.setResumeUrl(upload.getSecureUrl());
	    profile.setResumePublicId(upload.getPublicId());
	    profile.setUpdatedAt(LocalDateTime.now());

	    Profile saved = profileRepository.save(profile);

	    return mapToResponse(saved);
	}

}
