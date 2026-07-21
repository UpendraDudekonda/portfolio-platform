package com.upendra.portfolio.port.service.impl;


import org.springframework.stereotype.Service;

import com.upendra.portfolio.port.client.ContactClient;
import com.upendra.portfolio.port.dto.response.AdminDashboardResponse;
import com.upendra.portfolio.port.dto.response.ContactStatsResponse;
import com.upendra.portfolio.port.repository.CertificationRepository;
import com.upendra.portfolio.port.repository.EducationRepository;
import com.upendra.portfolio.port.repository.ExperienceRepository;
import com.upendra.portfolio.port.repository.ProfileRepository;
import com.upendra.portfolio.port.repository.ProjectRepository;
import com.upendra.portfolio.port.repository.SkillRepository;
import com.upendra.portfolio.port.repository.SocialLinkRepository;
import com.upendra.portfolio.port.service.AdminDashboardService;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class AdminDashboardServiceImpl 
        implements AdminDashboardService {


    private final ProfileRepository profileRepository;

    private final SkillRepository skillRepository;

    private final ProjectRepository projectRepository;

    private final ExperienceRepository experienceRepository;

    private final EducationRepository educationRepository;

    private final CertificationRepository certificationRepository;

    private final SocialLinkRepository socialLinkRepository;
    
    private final ContactClient contactClient;



    @Override
    public AdminDashboardResponse getDashboard() {


        ContactStatsResponse contactStats =
                contactClient.getContactStats();


        return AdminDashboardResponse.builder()

                .profileCompleted(true)

                .skills(skillRepository.count())

                .projects(projectRepository.count())

                .experience(experienceRepository.count())

                .education(educationRepository.count())

                .certifications(certificationRepository.count())

                .socialLinks(socialLinkRepository.count())

                .messages(
                    contactStats.getTotalMessages()
                )

                .unreadMessages(
                    contactStats.getUnreadMessages()
                )

                .build();
    }
}