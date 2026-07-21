package com.upendra.portfolio.port.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.upendra.portfolio.common.dto.ApiResponse;
import com.upendra.portfolio.common.exception.BadRequestException;
import com.upendra.portfolio.common.exception.ResourceNotFoundException;
import com.upendra.portfolio.port.client.MediaClient;
import com.upendra.portfolio.port.dto.media.UploadResponse;
import com.upendra.portfolio.port.dto.request.CreateProjectRequest;
import com.upendra.portfolio.port.dto.request.UpdateProjectRequest;
import com.upendra.portfolio.port.dto.response.ProjectImageResponse;
import com.upendra.portfolio.port.dto.response.ProjectResponse;
import com.upendra.portfolio.port.entity.Project;
import com.upendra.portfolio.port.entity.ProjectImage;
import com.upendra.portfolio.port.repository.ProjectImageRepository;
import com.upendra.portfolio.port.repository.ProjectRepository;
import com.upendra.portfolio.port.service.ProjectService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final MediaClient mediaClient;
    
    private final ProjectImageRepository projectImageRepository;

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

        List<ProjectImage> images =
                projectImageRepository
                        .findByProjectIdOrderByDisplayOrderAsc(projectId);

        for (ProjectImage image : images) {

            mediaClient.deleteMedia(
                    image.getImagePublicId(),
                    "image");
        }

        projectImageRepository.deleteByProjectId(projectId);

        projectRepository.delete(project);
    }

    private ProjectResponse mapToResponse(Project project) {
    	
    	List<ProjectImageResponse> images =
    	        projectImageRepository
    	                .findByProjectIdOrderByDisplayOrderAsc(project.getId())
    	                .stream()
    	                .map(image -> ProjectImageResponse.builder()
    	                        .id(image.getId())
    	                        .imageUrl(image.getImageUrl())
    	                        .displayOrder(image.getDisplayOrder())
    	                        .createdAt(image.getCreatedAt())
    	                        .build())
    	                .toList();

        return ProjectResponse.builder()
                .id(project.getId())
                .title(project.getTitle())
                .shortDescription(project.getShortDescription())
                .description(project.getDescription())
                .technologies(project.getTechnologies())
                .githubUrl(project.getGithubUrl())
                .liveUrl(project.getLiveUrl())
                .images(images)
                .featured(project.getFeatured())
                .displayOrder(project.getDisplayOrder())
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .build();
    }

    @Override
    public ProjectResponse uploadProjectImage(UUID userUuid,
                                              Long projectId,
                                              MultipartFile file) {

        Project project = projectRepository
                .findByIdAndUserUuid(projectId, userUuid)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project not found."));

        ApiResponse<UploadResponse> response =
                mediaClient.uploadImage(file, "project");

        UploadResponse upload = response.getData();

        long count = projectImageRepository.countByProjectId(projectId);

        ProjectImage projectImage = ProjectImage.builder()
                .projectId(projectId)
                .imageUrl(upload.getSecureUrl())
                .imagePublicId(upload.getPublicId())
                .displayOrder((int) count + 1)
                .build();

        projectImageRepository.save(projectImage);

        return mapToResponse(project);
    }

    @Override
    public List<ProjectImageResponse> getProjectImages(UUID userUuid,
                                                       Long projectId) {

        projectRepository.findByIdAndUserUuid(projectId, userUuid)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project not found."));

        return projectImageRepository
                .findByProjectIdOrderByDisplayOrderAsc(projectId)
                .stream()
                .map(image -> ProjectImageResponse.builder()
                        .id(image.getId())
                        .imageUrl(image.getImageUrl())
                        .displayOrder(image.getDisplayOrder())
                        .createdAt(image.getCreatedAt())
                        .build())
                .toList();
    }

    @Override
    public void deleteProjectImage(UUID userUuid,
                                   Long projectId,
                                   Long imageId) {

        projectRepository.findByIdAndUserUuid(projectId, userUuid)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project not found."));

        ProjectImage image = projectImageRepository
                .findByIdAndProjectId(imageId, projectId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project image not found."));

        mediaClient.deleteMedia(
                image.getImagePublicId(),
                "image");

        projectImageRepository.delete(image);
    }
    
    
    
}
