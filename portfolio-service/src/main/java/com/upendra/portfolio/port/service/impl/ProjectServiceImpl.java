package com.upendra.portfolio.port.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upendra.portfolio.common.exception.BadRequestException;
import com.upendra.portfolio.common.exception.ResourceNotFoundException;
import com.upendra.portfolio.port.dto.request.CreateProjectRequest;
import com.upendra.portfolio.port.dto.request.UpdateProjectRequest;
import com.upendra.portfolio.port.dto.response.ProjectResponse;
import com.upendra.portfolio.port.entity.Project;
import com.upendra.portfolio.port.repository.ProjectRepository;
import com.upendra.portfolio.port.service.ProjectService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public ProjectResponse createProject(UUID userUuid,
                                         CreateProjectRequest request) {

        if (projectRepository.existsByUserUuidAndTitle(
                userUuid,
                request.getTitle())) {

            throw new BadRequestException(
                    "Project with this title already exists.");
        }

        Project project = Project.builder()
                .userUuid(userUuid)
                .title(request.getTitle())
                .shortDescription(request.getShortDescription())
                .description(request.getDescription())
                .technologies(request.getTechnologies())
                .githubUrl(request.getGithubUrl())
                .liveUrl(request.getLiveUrl())
                .imageUrl(request.getImageUrl())
                .featured(request.getFeatured())
                .displayOrder(request.getDisplayOrder())
                .build();

        return mapToResponse(projectRepository.save(project));
    }

    @Override
    @Transactional
    public List<ProjectResponse> getMyProjects(UUID userUuid) {

        return projectRepository.findByUserUuidOrderByDisplayOrderAsc(userUuid)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ProjectResponse updateProject(UUID userUuid,
                                         Long projectId,
                                         UpdateProjectRequest request) {

        Project project = projectRepository
                .findByIdAndUserUuid(projectId, userUuid)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project not found."));

        if (request.getTitle() != null)
            project.setTitle(request.getTitle());

        if (request.getShortDescription() != null)
            project.setShortDescription(request.getShortDescription());

        if (request.getDescription() != null)
            project.setDescription(request.getDescription());

        if (request.getTechnologies() != null)
            project.setTechnologies(request.getTechnologies());

        if (request.getGithubUrl() != null)
            project.setGithubUrl(request.getGithubUrl());

        if (request.getLiveUrl() != null)
            project.setLiveUrl(request.getLiveUrl());

        if (request.getImageUrl() != null)
            project.setImageUrl(request.getImageUrl());

        if (request.getFeatured() != null)
            project.setFeatured(request.getFeatured());

        if (request.getDisplayOrder() != null)
            project.setDisplayOrder(request.getDisplayOrder());

        return mapToResponse(projectRepository.save(project));
    }

    @Override
    public void deleteProject(UUID userUuid,
                              Long projectId) {

        Project project = projectRepository
                .findByIdAndUserUuid(projectId, userUuid)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project not found."));

        projectRepository.delete(project);
    }

    private ProjectResponse mapToResponse(Project project) {

        return ProjectResponse.builder()
                .id(project.getId())
                .title(project.getTitle())
                .shortDescription(project.getShortDescription())
                .description(project.getDescription())
                .technologies(project.getTechnologies())
                .githubUrl(project.getGithubUrl())
                .liveUrl(project.getLiveUrl())
                .imageUrl(project.getImageUrl())
                .featured(project.getFeatured())
                .displayOrder(project.getDisplayOrder())
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .build();
    }
}
