package com.upendra.portfolio.port.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upendra.portfolio.common.exception.BadRequestException;
import com.upendra.portfolio.common.exception.ResourceNotFoundException;
import com.upendra.portfolio.port.dto.request.CreateEducationRequest;
import com.upendra.portfolio.port.dto.request.UpdateEducationRequest;
import com.upendra.portfolio.port.dto.response.EducationResponse;
import com.upendra.portfolio.port.entity.Education;
import com.upendra.portfolio.port.repository.EducationRepository;
import com.upendra.portfolio.port.service.EducationService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EducationServiceImpl implements EducationService{

	private final EducationRepository educationRepository;

	@Override
	public EducationResponse createEducation(UUID userUuid,
	                                         CreateEducationRequest request) {

	    validateEducation(
	            request.getStartDate(),
	            request.getEndDate(),
	            request.getCurrentlyStudying());

	    if (educationRepository.existsByUserUuidAndInstitutionNameAndDegree(
	            userUuid,
	            request.getInstitutionName(),
	            request.getDegree())) {

	        throw new BadRequestException(
	                "Education already exists for this institution and degree.");
	    }

	    Education education = Education.builder()
	            .userUuid(userUuid)
	            .institutionName(request.getInstitutionName())
	            .degree(request.getDegree())
	            .fieldOfStudy(request.getFieldOfStudy())
	            .startDate(request.getStartDate())
	            .endDate(request.getCurrentlyStudying() ? null : request.getEndDate())
	            .currentlyStudying(request.getCurrentlyStudying())
	            .grade(request.getGrade())
	            .description(request.getDescription())
	            .displayOrder(request.getDisplayOrder())
	            .build();

	    Education saved = educationRepository.save(education);

	    return mapToResponse(saved);
	}

	@Override
	@Transactional
	public List<EducationResponse> getEducations(UUID userUuid) {

	    return educationRepository
	            .findByUserUuidOrderByDisplayOrderAsc(userUuid)
	            .stream()
	            .map(this::mapToResponse)
	            .toList();
	}

	@Override
	public EducationResponse updateEducation(UUID userUuid,
	                                         Long educationId,
	                                         UpdateEducationRequest request) {

	    Education education = educationRepository
	            .findByIdAndUserUuid(educationId, userUuid)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException("Education not found."));

	    if (request.getInstitutionName() != null) {
	        education.setInstitutionName(request.getInstitutionName());
	    }

	    if (request.getDegree() != null) {
	        education.setDegree(request.getDegree());
	    }

	    if (request.getFieldOfStudy() != null) {
	        education.setFieldOfStudy(request.getFieldOfStudy());
	    }

	    if (request.getStartDate() != null) {
	        education.setStartDate(request.getStartDate());
	    }

	    if (request.getCurrentlyStudying() != null) {

	        education.setCurrentlyStudying(request.getCurrentlyStudying());

	        if (request.getCurrentlyStudying()) {
	            education.setEndDate(null);
	        } else if (request.getEndDate() != null) {
	            education.setEndDate(request.getEndDate());
	        }
	    }

	    if (request.getGrade() != null) {
	        education.setGrade(request.getGrade());
	    }

	    if (request.getDescription() != null) {
	        education.setDescription(request.getDescription());
	    }

	    if (request.getDisplayOrder() != null) {
	        education.setDisplayOrder(request.getDisplayOrder());
	    }

	    validateEducation(
	            education.getStartDate(),
	            education.getEndDate(),
	            education.getCurrentlyStudying());

	    Education saved = educationRepository.save(education);

	    return mapToResponse(saved);
	}

	@Override
	public void deleteEducation(UUID userUuid,
	                            Long educationId) {

	    Education education = educationRepository
	            .findByIdAndUserUuid(educationId, userUuid)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException("Education not found."));

	    educationRepository.delete(education);
	}
	
	
	private void validateEducation(LocalDate startDate,
									LocalDate endDate,
											Boolean currentlyStudying) {

			if (Boolean.FALSE.equals(currentlyStudying) && endDate == null) {
					throw new BadRequestException(
							"End date is required when currentlyStudying is false.");
				}

			if (startDate != null
					&& endDate != null
						&& endDate.isBefore(startDate)) {

				throw new BadRequestException(
							"End date cannot be before start date.");
				}
	}
	
	private EducationResponse mapToResponse(Education education) {

	    return EducationResponse.builder()
	            .id(education.getId())
	            .institutionName(education.getInstitutionName())
	            .degree(education.getDegree())
	            .fieldOfStudy(education.getFieldOfStudy())
	            .startDate(education.getStartDate())
	            .endDate(education.getEndDate())
	            .currentlyStudying(education.getCurrentlyStudying())
	            .grade(education.getGrade())
	            .description(education.getDescription())
	            .displayOrder(education.getDisplayOrder())
	            .createdAt(education.getCreatedAt())
	            .updatedAt(education.getUpdatedAt())
	            .build();
	}
	
	
}
