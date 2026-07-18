package com.upendra.portfolio.port.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateSkillRequest {
	
	@NotBlank(message = "Skill name is required.")
    @Size(max = 100, message = "Skill name cannot exceed 100 characters.")
    private String skillName;

    @NotNull(message = "Proficiency is required.")
    @Min(value = 0, message = "Proficiency must be between 0 and 100.")
    @Max(value = 100, message = "Proficiency must be between 0 and 100.")
    private Integer proficiency;

    @NotNull(message = "Display order is required.")
    @Min(value = 1, message = "Display order must be greater than 0.")
    private Integer displayOrder;
    
    

}
