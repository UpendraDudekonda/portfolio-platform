package com.upendra.portfolio.port.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upendra.portfolio.common.dto.ApiResponse;
import com.upendra.portfolio.port.dto.request.CreateSkillRequest;
import com.upendra.portfolio.port.dto.request.UpdateSkillRequest;
import com.upendra.portfolio.port.dto.response.SkillResponse;
import com.upendra.portfolio.port.service.SkillService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profile/skill")
public class SkillController {
	
	private final SkillService skillService;
	
	@PostMapping
	public ApiResponse<SkillResponse> createSkill(@RequestHeader("X-User-UUID") String userUuid,
														@Valid @RequestBody CreateSkillRequest request){
		
		SkillResponse skillResponse = skillService.createSkill(UUID.fromString(userUuid), request);
	
		return ApiResponse.<SkillResponse>builder()
				.success(true)
				.message("Skill created successful.")
				.data(skillResponse)
				.build();
	}
	
	@GetMapping
	public ApiResponse<List<SkillResponse>> getMySkills(
	        @RequestHeader("X-User-UUID") String userUuid) {

	    List<SkillResponse> skills =
	            skillService.getMySkills(UUID.fromString(userUuid));

	    return ApiResponse.<List<SkillResponse>>builder()
	            .success(true)
	            .message("Skills fetched successfully.")
	            .data(skills)
	            .build();
	}
	
	@PutMapping("/{skillId}")
	public ApiResponse<SkillResponse> updateSkill(
	        @RequestHeader("X-User-UUID") String userUuid,
	        @PathVariable Long skillId,
	        @Valid @RequestBody UpdateSkillRequest request) {

	    SkillResponse skillResponse =
	            skillService.updateSkill(
	                    UUID.fromString(userUuid),
	                    skillId,
	                    request);

	    return ApiResponse.<SkillResponse>builder()
	            .success(true)
	            .message("Skill updated successfully.")
	            .data(skillResponse)
	            .build();
	}
	
	@DeleteMapping("/{skillId}")
	public ApiResponse<Void> deleteSkill(
	        @RequestHeader("X-User-UUID") String userUuid,
	        @PathVariable Long skillId) {

	    skillService.deleteSkill(
	            UUID.fromString(userUuid),
	            skillId);

	    return ApiResponse.<Void>builder()
	            .success(true)
	            .message("Skill deleted successfully.")
	            .build();
	}

}
