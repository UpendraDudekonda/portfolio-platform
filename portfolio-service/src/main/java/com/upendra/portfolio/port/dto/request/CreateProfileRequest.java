package com.upendra.portfolio.port.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateProfileRequest {
	
	@NotBlank(message = "Full name is required.")
    @Size(max = 100, message = "Full name cannot exceed 100 characters.")
    private String fullName;

    @NotBlank(message = "Headline is required.")
    @Size(max = 150, message = "Headline cannot exceed 150 characters.")
    private String headline;

    @Size(max = 5000, message = "About section cannot exceed 5000 characters.")
    private String about;

    @Size(max = 100, message = "Location cannot exceed 100 characters.")
    private String location;

    private String profileImageUrl;

    private String resumeUrl;
    

}
