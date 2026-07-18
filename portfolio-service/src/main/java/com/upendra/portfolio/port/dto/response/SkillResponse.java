package com.upendra.portfolio.port.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SkillResponse {
	
	 	private Long id;

	    private String skillName;

	    private Integer proficiency;

	    private Integer displayOrder;

	    private LocalDateTime createdAt;

	    private LocalDateTime updatedAt;

}
