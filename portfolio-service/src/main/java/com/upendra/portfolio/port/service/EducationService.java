package com.upendra.portfolio.port.service;

import java.util.List;
import java.util.UUID;

import com.upendra.portfolio.port.dto.request.CreateEducationRequest;
import com.upendra.portfolio.port.dto.request.UpdateEducationRequest;
import com.upendra.portfolio.port.dto.response.EducationResponse;

public interface EducationService {

    EducationResponse createEducation(
            UUID userUuid,
            CreateEducationRequest request);

    List<EducationResponse> getEducations(UUID userUuid);

    EducationResponse updateEducation(
            UUID userUuid,
            Long educationId,
            UpdateEducationRequest request);

    void deleteEducation(
            UUID userUuid,
            Long educationId);
}