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
            @RequestHeader("X-User-UUID") String userUuid,
            @Valid @RequestBody CreateEducationRequest request) {

        return ApiResponse.<EducationResponse>builder()
                .success(true)
                .message("Education created successfully.")
                .data(educationService.createEducation(
                        UUID.fromString(userUuid),
                        request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<EducationResponse>> getMyEducations(
            @RequestHeader("X-User-UUID") String userUuid) {

        return ApiResponse.<List<EducationResponse>>builder()
                .success(true)
                .message("Educations fetched successfully.")
                .data(educationService.getEducations(
                        UUID.fromString(userUuid)))
                .build();
    }

    @PutMapping("/{educationId}")
    public ApiResponse<EducationResponse> updateEducation(
            @RequestHeader("X-User-UUID") String userUuid,
            @PathVariable Long educationId,
            @Valid @RequestBody UpdateEducationRequest request) {

        return ApiResponse.<EducationResponse>builder()
                .success(true)
                .message("Education updated successfully.")
                .data(educationService.updateEducation(
                        UUID.fromString(userUuid),
                        educationId,
                        request))
                .build();
    }

    @DeleteMapping("/{educationId}")
    public ApiResponse<Void> deleteEducation(
            @RequestHeader("X-User-UUID") String userUuid,
            @PathVariable Long educationId) {

        educationService.deleteEducation(
                UUID.fromString(userUuid),
                educationId);

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Education deleted successfully.")
                .build();
    }
}