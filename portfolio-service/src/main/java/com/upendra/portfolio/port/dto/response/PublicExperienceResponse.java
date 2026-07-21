package com.upendra.portfolio.port.dto.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PublicExperienceResponse {

    private String companyName;

    private String jobTitle;

    private String employmentType;

    private String location;

    private Boolean currentlyWorking;

    private LocalDate startDate;

    private LocalDate endDate;

    private String description;

    private Integer displayOrder;
}