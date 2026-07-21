package com.upendra.portfolio.port.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upendra.portfolio.common.exception.ResourceNotFoundException;
import com.upendra.portfolio.port.dto.response.PublicCertificationResponse;
import com.upendra.portfolio.port.dto.response.PublicEducationResponse;
import com.upendra.portfolio.port.dto.response.PublicExperienceResponse;
import com.upendra.portfolio.port.dto.response.PublicPortfolioResponse;
import com.upendra.portfolio.port.dto.response.PublicProfileResponse;
import com.upendra.portfolio.port.dto.response.PublicProjectImageResponse;
import com.upendra.portfolio.port.dto.response.PublicProjectResponse;
import com.upendra.portfolio.port.dto.response.PublicSkillResponse;
import com.upendra.portfolio.port.dto.response.PublicSocialLinkResponse;
import com.upendra.portfolio.port.entity.Profile;
import com.upendra.portfolio.port.repository.CertificationRepository;
import com.upendra.portfolio.port.repository.EducationRepository;
import com.upendra.portfolio.port.repository.ExperienceRepository;
import com.upendra.portfolio.port.repository.ProfileRepository;
import com.upendra.portfolio.port.repository.ProjectImageRepository;
import com.upendra.portfolio.port.repository.ProjectRepository;
import com.upendra.portfolio.port.repository.SkillRepository;
import com.upendra.portfolio.port.repository.SocialLinkRepository;
import com.upendra.portfolio.port.service.PublicService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublicServiceImpl implements PublicService {

    private final ProfileRepository profileRepository;
    private final SkillRepository skillRepository;
    private final ExperienceRepository experienceRepository;
    private final EducationRepository educationRepository;
    private final ProjectRepository projectRepository;
    private final ProjectImageRepository projectImageRepository;
    private final CertificationRepository certificationRepository;
    private final SocialLinkRepository socialLinkRepository;

    @Override
    public PublicPortfolioResponse getPortfolio() {

        Profile profile = profileRepository
                .findFirstByOrderByIdAsc()
                .orElseThrow(() ->
                        new ResourceNotFoundException("Portfolio not found."));

        UUID userUuid = profile.getUserUuid();

        return PublicPortfolioResponse.builder()

                .profile(mapProfile(profile))

                .skills(
                        skillRepository.findByUserUuidOrderByDisplayOrderAsc(userUuid)
                                .stream()
                                .map(skill -> PublicSkillResponse.builder()
                                       
                                        .skillName(skill.getSkillName())
                                        .proficiency(skill.getProficiency())
                                        .displayOrder(skill.getDisplayOrder())
                                       
                                        .build())
                                .toList())

                .experiences(
                        experienceRepository.findByUserUuidOrderByDisplayOrderAsc(userUuid)
                                .stream()
                                .map(exp -> PublicExperienceResponse.builder()
                                        
                                        .companyName(exp.getCompanyName())
                                        .jobTitle(exp.getJobTitle())
                                        .employmentType(exp.getEmploymentType())
                                        .location(exp.getLocation())
                                        .currentlyWorking(exp.getCurrentlyWorking())
                                        .startDate(exp.getStartDate())
                                        .endDate(exp.getEndDate())
                                        .description(exp.getDescription())
                                        .displayOrder(exp.getDisplayOrder() == null ? 1 : exp.getDisplayOrder()
                                        		)
                                      
                                        .build())
                                .toList())

                .educations(
                        educationRepository.findByUserUuidOrderByDisplayOrderAsc(userUuid)
                                .stream()
                                .map(education -> PublicEducationResponse.builder()
                                      
                                        .institutionName(education.getInstitutionName())
                                        .degree(education.getDegree())
                                        .fieldOfStudy(education.getFieldOfStudy())
                                        .startDate(education.getStartDate())
                                        .endDate(education.getEndDate())
                                        .currentlyStudying(education.getCurrentlyStudying())
                                        .grade(education.getGrade())
                                        .description(education.getDescription())
                                        .displayOrder(education.getDisplayOrder())
                                      
                                        .build())
                                .toList())

                .projects(
                        projectRepository.findByUserUuidOrderByDisplayOrderAsc(userUuid)
                                .stream()
                                .map(project -> {

                                    List<PublicProjectImageResponse> images =
                                            projectImageRepository
                                                    .findByProjectIdOrderByDisplayOrderAsc(project.getId())
                                                    .stream()
                                                    .map(image -> PublicProjectImageResponse.builder()
                                                            
                                                            .imageUrl(image.getImageUrl())
                                                            .displayOrder(image.getDisplayOrder())
                                                           
                                                            .build())
                                                    .toList();

                                    return PublicProjectResponse.builder()
                                           
                                            .title(project.getTitle())
                                            .shortDescription(project.getShortDescription())
                                            .description(project.getDescription())
                                            .technologies(project.getTechnologies())
                                            .githubUrl(project.getGithubUrl())
                                            .liveUrl(project.getLiveUrl())
                                            .images(images)
                                            .featured(project.getFeatured())
                                            .displayOrder(project.getDisplayOrder())
                                          
                                            .build();
                                })
                                .toList())

                .certifications(
                        certificationRepository.findByUserUuidOrderByDisplayOrderAsc(userUuid)
                                .stream()
                                .map(cert -> PublicCertificationResponse.builder()
                                        
                                        .name(cert.getName())
                                        .issuingOrganization(cert.getIssuingOrganization())
                                        .issueDate(cert.getIssueDate())
                                        .expiryDate(cert.getExpiryDate())
                                        .credentialId(cert.getCredentialId())
                                        .credentialUrl(cert.getCredentialUrl())
                                        .certificateUrl(cert.getCertificateUrl())
                                        .displayOrder(cert.getDisplayOrder())
                                        
                                        .build())
                                .toList())

                .socialLinks(
                        socialLinkRepository.findByUserUuidOrderByDisplayOrderAsc(userUuid)
                                .stream()
                                .map(link -> PublicSocialLinkResponse.builder()
                                        .platform(link.getPlatform())
                                        .url(link.getUrl())
                                        .displayOrder(link.getDisplayOrder())
                                        .build())
                                .toList()
                )

                .build();
    }

//    private ProfileResponse mapProfile(Profile profile) {
//
//        return ProfileResponse.builder()
//                .id(profile.getId())
//                .userUuid(profile.getUserUuid())
//                .fullName(profile.getFullName())
//                .headline(profile.getHeadline())
//                .about(profile.getAbout())
//                .location(profile.getLocation())
//                .profileImageUrl(profile.getProfileImageUrl())
//                .resumeUrl(profile.getResumeUrl())
//                .createdAt(profile.getCreatedAt())
//                .updatedAt(profile.getUpdatedAt())
//                .build();
//    }
    
    
    private PublicProfileResponse mapProfile(Profile profile){

        return PublicProfileResponse.builder()
                .fullName(profile.getFullName())
                .headline(profile.getHeadline())
                .about(profile.getAbout())
                .location(profile.getLocation())
                .profileImageUrl(profile.getProfileImageUrl())
                .resumeUrl(profile.getResumeUrl())
                .build();
    }
}