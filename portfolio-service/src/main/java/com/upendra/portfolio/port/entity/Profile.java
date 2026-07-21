package com.upendra.portfolio.port.entity;

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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="profile")
public class Profile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private UUID userUuid;

	@Column
	private String fullName;

	@Column
	private String headline;

	@Column(length = 5000)
	private String about;

	@Column
	private String location;

	@Column
	private String profileImageUrl;

	@Column
	private String profileImagePublicId;

	@Column
	private String resumeUrl;

	@Column
	private String resumePublicId;

	@Column
	private LocalDateTime createdAt;

	@Column
	private LocalDateTime updatedAt;

}
