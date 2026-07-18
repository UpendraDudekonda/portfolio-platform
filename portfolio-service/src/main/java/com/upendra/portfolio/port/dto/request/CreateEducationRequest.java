package com.upendra.portfolio.port.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateEducationRequest {

    @NotBlank
    private String institutionName;

    @NotBlank
    private String degree;

    private String fieldOfStudy;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

    @NotNull
    private Boolean currentlyStudying;

    private String grade;

    private String description;

    @NotNull
    private Integer displayOrder;
}
