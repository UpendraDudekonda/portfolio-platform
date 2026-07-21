package com.upendra.portfolio.port.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PublicPortfolioResponse {

    private PublicProfileResponse profile;

    private List<PublicSkillResponse> skills;

    private List<PublicExperienceResponse> experiences;

    private List<PublicEducationResponse> educations;

    private List<PublicProjectResponse> projects;

    private List<PublicCertificationResponse> certifications;

    private List<PublicSocialLinkResponse> socialLinks;

}