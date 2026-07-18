package com.upendra.portfolio.port.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateExperienceRequest {

    @NotBlank
    private String companyName;

    @NotBlank
    private String jobTitle;

    @NotBlank
    private String employmentType;

    @NotBlank
    private String location;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

    @NotNull
    private Boolean currentlyWorking;

    @NotBlank
    private String description;

    @NotNull
    private Integer displayOrder;
}
