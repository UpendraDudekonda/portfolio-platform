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
import com.upendra.portfolio.port.dto.request.CreateProjectRequest;
import com.upendra.portfolio.port.dto.request.UpdateProjectRequest;
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
}
