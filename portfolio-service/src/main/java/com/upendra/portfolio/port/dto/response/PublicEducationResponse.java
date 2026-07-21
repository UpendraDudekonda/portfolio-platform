package com.upendra.portfolio.port.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicEducationResponse {
	
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
