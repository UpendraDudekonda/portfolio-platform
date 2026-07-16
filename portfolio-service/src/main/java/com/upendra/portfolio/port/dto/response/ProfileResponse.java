package com.upendra.portfolio.port.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfileResponse {

	private Long id;

    private UUID userUuid;

    private String fullName;

    private String headline;

    private String about;

    private String location;

    private String profileImageUrl;

    private String resumeUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
