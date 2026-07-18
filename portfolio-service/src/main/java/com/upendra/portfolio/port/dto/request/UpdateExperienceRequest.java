package com.upendra.portfolio.port.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateExperienceRequest {

    
    private String companyName;


    private String jobTitle;

   
    private String employmentType;

    
    private String location;

   
    private LocalDate startDate;

    private LocalDate endDate;

   
    private Boolean currentlyWorking;

   
    private String description;

   
    private Integer displayOrder;
}
