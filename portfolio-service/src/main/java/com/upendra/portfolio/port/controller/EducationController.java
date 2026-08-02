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
import com.upendra.portfolio.port.dto.request.CreateEducationRequest;
import com.upendra.portfolio.port.dto.request.UpdateEducationRequest;
import com.upendra.portfolio.port.dto.response.EducationResponse;
import com.upendra.portfolio.port.service.EducationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/profile/education")
@RequiredArgsConstructor
public class EducationController {

    private final EducationService educationService;

    @PostMapping
    public ApiResponse<EducationResponse> createEducation(
            
            @Valid @RequestBody CreateEducationRequest request) {
    	
    	UUID userUuid = SecurityUtils.getCurrentUserUuid();
        return ApiResponse.<EducationResponse>builder()
                .success(true)
                .message("Education created successfully.")
                .data(educationService.createEducation(
                		userUuid,
                        request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<EducationResponse>> getMyEducations(
            ) {
    	
    	UUID userUuid = SecurityUtils.getCurrentUserUuid();
        return ApiResponse.<List<EducationResponse>>builder()
                .success(true)
                .message("Educations fetched successfully.")
                .data(educationService.getEducations(
                		userUuid))
                .build();
    }

    @PutMapping("/{educationId}")
    public ApiResponse<EducationResponse> updateEducation(
          
            @PathVariable Long educationId,
            @Valid @RequestBody UpdateEducationRequest request) {

    	UUID userUuid = SecurityUtils.getCurrentUserUuid();
        return ApiResponse.<EducationResponse>builder()
                .success(true)
                .message("Education updated successfully.")
                .data(educationService.updateEducation(
                		userUuid,
                        educationId,
                        request))
                .build();
    }

    @DeleteMapping("/{educationId}")
    public ApiResponse<Void> deleteEducation(
           
            @PathVariable Long educationId) {

    	UUID userUuid = SecurityUtils.getCurrentUserUuid();
        educationService.deleteEducation(
        		userUuid,
                educationId);

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Education deleted successfully.")
                .build();
    }
}