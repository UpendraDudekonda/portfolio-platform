package com.upendra.portfolio.port.service;

import java.util.List;
import java.util.UUID;

import com.upendra.portfolio.port.dto.request.CreateSkillRequest;
import com.upendra.portfolio.port.dto.request.UpdateSkillRequest;
import com.upendra.portfolio.port.dto.response.SkillResponse;

public interface SkillService {
	
	SkillResponse createSkill(UUID userUuid, CreateSkillRequest request);

    List<SkillResponse> getMySkills(UUID userUuid);

    SkillResponse updateSkill(UUID userUuid,
                              Long skillId,
                              UpdateSkillRequest request);

    void deleteSkill(UUID userUuid,
                     Long skillId);

}
