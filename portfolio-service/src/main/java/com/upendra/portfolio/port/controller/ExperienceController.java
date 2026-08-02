package com.upendra.portfolio.port.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upendra.portfolio.auth.security.SecurityUtils;
import com.upendra.portfolio.common.dto.ApiResponse;
import com.upendra.portfolio.port.dto.request.CreateExperienceRequest;
import com.upendra.portfolio.port.dto.request.UpdateExperienceRequest;
import com.upendra.portfolio.port.dto.response.ExperienceResponse;
import com.upendra.portfolio.port.service.ExperienceService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/profile/experience")
@RequiredArgsConstructor
public class ExperienceController {

    private final ExperienceService experienceService;

    @PostMapping
    public ApiResponse<ExperienceResponse> createExperience(
            
            @Valid @RequestBody CreateExperienceRequest request) {

    	UUID userUuid = SecurityUtils.getCurrentUserUuid();
    	
        return ApiResponse.<ExperienceResponse>builder()
                .success(true)
                .message("Experience created successfully.")
                .data(experienceService.createExperience(
                		userUuid,
                        request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<ExperienceResponse>> getMyExperiences(
            ) {

    	UUID userUuid = SecurityUtils.getCurrentUserUuid();
    	
        return ApiResponse.<List<ExperienceResponse>>builder()
                .success(true)
                .message("Experiences fetched successfully.")
                .data(experienceService.getMyExperiences(
                		userUuid))
                .build();
    }

    @PutMapping("/{experienceId}")
    public ApiResponse<ExperienceResponse> updateExperience(
           
            @PathVariable Long experienceId,
            @Valid @RequestBody UpdateExperienceRequest request) {

    	UUID userUuid = SecurityUtils.getCurrentUserUuid();
    	
        return ApiResponse.<ExperienceResponse>builder()
                .success(true)
                .message("Experience updated successfully.")
                .data(experienceService.updateExperience(
                		userUuid,
                        experienceId,
                        request))
                .build();
    }

    @DeleteMapping("/{experienceId}")
    public ApiResponse<Void> deleteExperience(
            
            @PathVariable Long experienceId) {

    	UUID userUuid = SecurityUtils.getCurrentUserUuid();
        experienceService.deleteExperience(
        		userUuid,
                experienceId);

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Experience deleted successfully.")
                .build();
    }
}
