package com.upendra.portfolio.port.dto.response;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectResponse {

    private Long id;

    private String title;

    private String shortDescription;

    private String description;

    private String technologies;

    private String githubUrl;

    private String liveUrl;

    private String imageUrl;

    private Boolean featured;

    private Integer displayOrder;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}