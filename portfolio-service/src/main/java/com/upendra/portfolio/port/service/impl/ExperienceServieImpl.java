package com.upendra.portfolio.port.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upendra.portfolio.common.exception.BadRequestException;
import com.upendra.portfolio.common.exception.ResourceNotFoundException;
import com.upendra.portfolio.port.dto.request.CreateExperienceRequest;
import com.upendra.portfolio.port.dto.request.UpdateExperienceRequest;
import com.upendra.portfolio.port.dto.response.ExperienceResponse;
import com.upendra.portfolio.port.entity.Experience;
import com.upendra.portfolio.port.repository.ExperienceRepository;
import com.upendra.portfolio.port.service.ExperienceService;

@Service
public class ExperienceServieImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;

    public ExperienceServieImpl(ExperienceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }

    @Override
    public ExperienceResponse createExperience(UUID userUuid,
                                               CreateExperienceRequest request) {

    	validateExperience(
    	        request.getStartDate(),
    	        request.getEndDate(),
    	        request.getCurrentlyWorking());

        Experience experience = Experience.builder()
                .userUuid(userUuid)
                .companyName(request.getCompanyName())
                .jobTitle(request.getJobTitle())
                .employmentType(request.getEmploymentType())
                .location(request.getLocation())
                .startDate(request.getStartDate())
                .endDate(request.getCurrentlyWorking() ? null : request.getEndDate())
                .currentlyWorking(request.getCurrentlyWorking())
                .description(request.getDescription())
                .displayOrder(request.getDisplayOrder())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Experience saved = experienceRepository.save(experience);

        return mapToResponse(saved);
    }

    @Override
    public List<ExperienceResponse> getMyExperiences(UUID userUuid) {

        return experienceRepository
                .findByUserUuidOrderByDisplayOrderAsc(userUuid)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ExperienceResponse updateExperience(UUID userUuid,
                                               Long experienceId,
                                               UpdateExperienceRequest request) {

        Experience experience = experienceRepository
                .findByIdAndUserUuid(experienceId, userUuid)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Experience not found."));


        if (request.getCompanyName() != null) {
            experience.setCompanyName(request.getCompanyName());
        }

        if (request.getJobTitle() != null) {
            experience.setJobTitle(request.getJobTitle());
        }

        if (request.getEmploymentType() != null) {
            experience.setEmploymentType(request.getEmploymentType());
        }

        if (request.getLocation() != null) {
            experience.setLocation(request.getLocation());
        }

        if (request.getStartDate() != null) {
            experience.setStartDate(request.getStartDate());
        }

        if (request.getCurrentlyWorking() != null) {

            experience.setCurrentlyWorking(request.getCurrentlyWorking());

            if (request.getCurrentlyWorking()) {
                experience.setEndDate(null);
            } else if (request.getEndDate() != null) {
                experience.setEndDate(request.getEndDate());
            }
        }


        if (request.getDescription() != null) {
            experience.setDescription(request.getDescription());
        }

        if (request.getDisplayOrder() != null) {
            experience.setDisplayOrder(request.getDisplayOrder());
        }


        experience.setUpdatedAt(LocalDateTime.now());

        Experience saved = experienceRepository.save(experience);

        return mapToResponse(saved);
    }
    
    private void validateExperience(
            LocalDate startDate,
            LocalDate endDate,
            Boolean currentlyWorking) {

        // Validate only when currentlyWorking value is provided
        if (Boolean.FALSE.equals(currentlyWorking) && endDate == null) {
            throw new BadRequestException(
                    "End date is required when currentlyWorking is false.");
        }

        // Compare dates only when both dates exist
        if (startDate != null 
                && endDate != null 
                && endDate.isBefore(startDate)) {

            throw new BadRequestException(
                    "End date cannot be before start date.");
        }
    }

    @Override
    public void deleteExperience(UUID userUuid,
                                 Long experienceId) {

        Experience experience = experienceRepository
                .findByIdAndUserUuid(experienceId, userUuid)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Experience not found."));

        experienceRepository.delete(experience);
    }

 

    /**
     * Entity -> DTO
     */
    private ExperienceResponse mapToResponse(Experience experience) {

        return ExperienceResponse.builder()
                .id(experience.getId())
                .companyName(experience.getCompanyName())
                .jobTitle(experience.getJobTitle())
                .employmentType(experience.getEmploymentType())
                .location(experience.getLocation())
                .startDate(experience.getStartDate())
                .endDate(experience.getEndDate())
                .currentlyWorking(experience.getCurrentlyWorking())
                .description(experience.getDescription())
                .displayOrder(experience.getDisplayOrder())
                .createdAt(experience.getCreatedAt())
                .updatedAt(experience.getUpdatedAt())
                .build();
    }
}