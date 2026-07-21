package com.upendra.portfolio.port.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.upendra.portfolio.common.dto.ApiResponse;
import com.upendra.portfolio.port.dto.request.CreateProjectRequest;
import com.upendra.portfolio.port.dto.request.UpdateProjectRequest;
import com.upendra.portfolio.port.dto.response.ProjectImageResponse;
import com.upendra.portfolio.port.dto.response.ProjectResponse;
import com.upendra.portfolio.port.service.ProjectService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/profile/project")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ApiResponse<ProjectResponse> createProject(
            @RequestHeader("X-User-UUID") String userUuid,
            @Valid @RequestBody CreateProjectRequest request) {

        return ApiResponse.<ProjectResponse>builder()
                .success(true)
                .message("Project created successfully.")
                .data(projectService.createProject(
                        UUID.fromString(userUuid),
                        request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<ProjectResponse>> getProjects(
            @RequestHeader("X-User-UUID") String userUuid) {

        return ApiResponse.<List<ProjectResponse>>builder()
                .success(true)
                .message("Projects fetched successfully.")
                .data(projectService.getMyProjects(
                        UUID.fromString(userUuid)))
                .build();
    }

    @PutMapping("/{projectId}")
    public ApiResponse<ProjectResponse> updateProject(
            @RequestHeader("X-User-UUID") String userUuid,
            @PathVariable Long projectId,
            @Valid @RequestBody UpdateProjectRequest request) {

        return ApiResponse.<ProjectResponse>builder()
                .success(true)
                .message("Project updated successfully.")
                .data(projectService.updateProject(
                        UUID.fromString(userUuid),
                        projectId,
                        request))
                .build();
    }

    @DeleteMapping("/{projectId}")
    public ApiResponse<Void> deleteProject(
            @RequestHeader("X-User-UUID") String userUuid,
            @PathVariable Long projectId) {

        projectService.deleteProject(
                UUID.fromString(userUuid),
                projectId);

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Project deleted successfully.")
                .build();
    }
    
    
    @PostMapping(
    	    value="/{projectId}/image",
    	    consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    	)
    	public ApiResponse<ProjectResponse> uploadProjectImage(
    	        @RequestHeader("X-User-UUID") String userUuid,
    	        @PathVariable Long projectId,
    	        @RequestPart("file") MultipartFile file) {

    	    return ApiResponse.<ProjectResponse>builder()
    	            .success(true)
    	            .message("Project image uploaded successfully.")
    	            .data(projectService.uploadProjectImage(
    	                    UUID.fromString(userUuid),
    	                    projectId,
    	                    file))
    	            .build();
    	}
    
    @GetMapping("/{projectId}/images")
    public ApiResponse<List<ProjectImageResponse>> getProjectImages(
            @RequestHeader("X-User-UUID") String userUuid,
            @PathVariable Long projectId) {

        return ApiResponse.<List<ProjectImageResponse>>builder()
                .success(true)
                .message("Project images fetched successfully.")
                .data(projectService.getProjectImages(
                        UUID.fromString(userUuid),
                        projectId))
                .build();
    }
    
    @DeleteMapping("/{projectId}/images/{imageId}")
    public ApiResponse<Void> deleteProjectImage(
            @RequestHeader("X-User-UUID") String userUuid,
            @PathVariable Long projectId,
            @PathVariable Long imageId) {

        projectService.deleteProjectImage(
                UUID.fromString(userUuid),
                projectId,
                imageId);

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Project image deleted successfully.")
                .build();
    }
}
