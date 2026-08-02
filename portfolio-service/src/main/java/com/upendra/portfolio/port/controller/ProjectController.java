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

import com.upendra.portfolio.auth.security.SecurityUtils;
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
           
            @Valid @RequestBody CreateProjectRequest request) {
    	
    	UUID userUuid = SecurityUtils.getCurrentUserUuid();

        return ApiResponse.<ProjectResponse>builder()
                .success(true)
                .message("Project created successfully.")
                .data(projectService.createProject(
                		userUuid,
                        request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<ProjectResponse>> getProjects() {
    	
    	UUID userUuid = SecurityUtils.getCurrentUserUuid();

        return ApiResponse.<List<ProjectResponse>>builder()
                .success(true)
                .message("Projects fetched successfully.")
                .data(projectService.getMyProjects(
                		userUuid))
                .build();
    }

    @PutMapping("/{projectId}")
    public ApiResponse<ProjectResponse> updateProject(
          
            @PathVariable Long projectId,
            @Valid @RequestBody UpdateProjectRequest request) {
    	
    	UUID userUuid = SecurityUtils.getCurrentUserUuid();

        return ApiResponse.<ProjectResponse>builder()
                .success(true)
                .message("Project updated successfully.")
                .data(projectService.updateProject(
                		userUuid,
                        projectId,
                        request))
                .build();
    }

    @DeleteMapping("/{projectId}")
    public ApiResponse<Void> deleteProject(
          
            @PathVariable Long projectId) {
    	
    	UUID userUuid = SecurityUtils.getCurrentUserUuid();
    	
        projectService.deleteProject(
        		userUuid,
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
    	       
    	        @PathVariable Long projectId,
    	        @RequestPart("file") MultipartFile file) {
    	
    	UUID userUuid = SecurityUtils.getCurrentUserUuid();

    	    return ApiResponse.<ProjectResponse>builder()
    	            .success(true)
    	            .message("Project image uploaded successfully.")
    	            .data(projectService.uploadProjectImage(
    	            		userUuid,
    	                    projectId,
    	                    file))
    	            .build();
    	}
    
    @GetMapping("/{projectId}/images")
    public ApiResponse<List<ProjectImageResponse>> getProjectImages(
           
            @PathVariable Long projectId) {
    	
    	UUID userUuid = SecurityUtils.getCurrentUserUuid();

        return ApiResponse.<List<ProjectImageResponse>>builder()
                .success(true)
                .message("Project images fetched successfully.")
                .data(projectService.getProjectImages(
                		userUuid,
                        projectId))
                .build();
    }
    
    @DeleteMapping("/{projectId}/images/{imageId}")
    public ApiResponse<Void> deleteProjectImage(
            
            @PathVariable Long projectId,
            @PathVariable Long imageId) {
    	
    	UUID userUuid = SecurityUtils.getCurrentUserUuid();

        projectService.deleteProjectImage(
        		userUuid,
                projectId,
                imageId);

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Project image deleted successfully.")
                .build();
    }
}
