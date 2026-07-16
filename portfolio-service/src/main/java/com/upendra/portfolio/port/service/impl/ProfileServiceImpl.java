package com.upendra.portfolio.port.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upendra.portfolio.common.exception.DuplicateResourceException;
import com.upendra.portfolio.common.exception.ResourceNotFoundException;
import com.upendra.portfolio.port.dto.request.CreateProfileRequest;
import com.upendra.portfolio.port.dto.request.UpdateProfileRequest;
import com.upendra.portfolio.port.dto.response.ProfileResponse;
import com.upendra.portfolio.port.entity.Profile;
import com.upendra.portfolio.port.repository.ProfileRepository;
import com.upendra.portfolio.port.service.ProfileService;

@Service
public class ProfileServiceImpl implements ProfileService{

	private final ProfileRepository profileRepository;
	
	
	
	
	public ProfileServiceImpl(ProfileRepository profileRepository) {
		super();
		this.profileRepository = profileRepository;
	}

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
		        .profileImageUrl(request.getProfileImageUrl())
		        .resumeUrl(request.getResumeUrl())
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
	            .resumeUrl(profile.getResumeUrl())
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

		if (request.getProfileImageUrl() != null) {
		    profile.setProfileImageUrl(request.getProfileImageUrl());
		}

		if (request.getResumeUrl() != null) {
		    profile.setResumeUrl(request.getResumeUrl());
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

}
