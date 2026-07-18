package com.upendra.portfolio.port.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upendra.portfolio.common.exception.DuplicateResourceException;
import com.upendra.portfolio.common.exception.ResourceNotFoundException;
import com.upendra.portfolio.port.dto.request.CreateSkillRequest;
import com.upendra.portfolio.port.dto.request.UpdateSkillRequest;
import com.upendra.portfolio.port.dto.response.SkillResponse;
import com.upendra.portfolio.port.entity.Skill;
import com.upendra.portfolio.port.repository.SkillRepository;
import com.upendra.portfolio.port.service.SkillService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {
	
	private final SkillRepository skillRepository;

   

    @Override
    public SkillResponse createSkill(UUID userUuid,
                                     CreateSkillRequest request) {

        if (skillRepository.existsByUserUuidAndSkillName(
                userUuid,
                request.getSkillName())) {

            throw new DuplicateResourceException("Skill already exists.");
        }

        Skill skill = Skill.builder()
                .userUuid(userUuid)
                .skillName(request.getSkillName())
                .proficiency(request.getProficiency())
                .displayOrder(request.getDisplayOrder())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Skill saved = skillRepository.save(skill);

        return mapToResponse(saved);
    }

    @Override
    public List<SkillResponse> getMySkills(UUID userUuid) {

        return skillRepository.findByUserUuidOrderByDisplayOrderAsc(userUuid)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public SkillResponse updateSkill(UUID userUuid,
                                     Long skillId,
                                     UpdateSkillRequest request) {

        Skill skill = skillRepository
                .findByIdAndUserUuid(skillId, userUuid)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Skill not found."));
        
        if (!skill.getSkillName().equalsIgnoreCase(request.getSkillName())
                && skillRepository.existsByUserUuidAndSkillName(userUuid, request.getSkillName())) {

            throw new DuplicateResourceException("Skill already exists.");
        }

        skill.setSkillName(request.getSkillName());
        skill.setProficiency(request.getProficiency());
        skill.setDisplayOrder(request.getDisplayOrder());
        skill.setUpdatedAt(LocalDateTime.now());

        Skill saved = skillRepository.save(skill);

        return mapToResponse(saved);
    }

    @Override
    public void deleteSkill(UUID userUuid,
                            Long skillId) {

        Skill skill = skillRepository
                .findByIdAndUserUuid(skillId, userUuid)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Skill not found."));

        skillRepository.delete(skill);
    }

    private SkillResponse mapToResponse(Skill skill) {

        return SkillResponse.builder()
                .id(skill.getId())
                .skillName(skill.getSkillName())
                .proficiency(skill.getProficiency())
                .displayOrder(skill.getDisplayOrder())
                .createdAt(skill.getCreatedAt())
                .updatedAt(skill.getUpdatedAt())
                .build();
    }

}
