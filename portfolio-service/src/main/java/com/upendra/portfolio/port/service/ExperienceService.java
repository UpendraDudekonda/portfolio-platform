package com.upendra.portfolio.port.service;

import java.util.List;
import java.util.UUID;

import com.upendra.portfolio.port.dto.request.CreateExperienceRequest;
import com.upendra.portfolio.port.dto.request.UpdateExperienceRequest;
import com.upendra.portfolio.port.dto.response.ExperienceResponse;

public interface ExperienceService {

    ExperienceResponse createExperience(
            UUID userUuid,
            CreateExperienceRequest request);

    List<ExperienceResponse> getMyExperiences(
            UUID userUuid);

    ExperienceResponse updateExperience(
            UUID userUuid,
            Long experienceId,
            UpdateExperienceRequest request);

    void deleteExperience(
            UUID userUuid,
            Long experienceId);
}