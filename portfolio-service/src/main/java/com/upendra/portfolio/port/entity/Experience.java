package com.upendra.portfolio.port.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "experience")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Experience {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private UUID userUuid;

	    private String companyName;

	    private String jobTitle;

	    private String employmentType;

	    private String location;

	    private LocalDate startDate;

	    private LocalDate endDate;

	    private Boolean currentlyWorking;

	    @Column(length = 3000)
	    private String description;

	    private Integer displayOrder;

	    private LocalDateTime createdAt;

	    private LocalDateTime updatedAt;
}
