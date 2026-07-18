package com.upendra.portfolio.port.service;

import java.util.List;
import java.util.UUID;

import com.upendra.portfolio.port.dto.request.CreateProjectRequest;
import com.upendra.portfolio.port.dto.request.UpdateProjectRequest;
import com.upendra.portfolio.port.dto.response.ProjectResponse;

public interface ProjectService {

    ProjectResponse createProject(
            UUID userUuid,
            CreateProjectRequest request);

    List<ProjectResponse> getMyProjects(
            UUID userUuid);

    ProjectResponse updateProject(
            UUID userUuid,
            Long projectId,
            UpdateProjectRequest request);

    void deleteProject(
            UUID userUuid,
            Long projectId);
}