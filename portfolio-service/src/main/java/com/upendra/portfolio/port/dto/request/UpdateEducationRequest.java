package com.upendra.portfolio.port.dto.request;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEducationRequest {

    private String institutionName;

    private String degree;

    private String fieldOfStudy;

    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean currentlyStudying;

    private String grade;

    private String description;

    private Integer displayOrder;
}
