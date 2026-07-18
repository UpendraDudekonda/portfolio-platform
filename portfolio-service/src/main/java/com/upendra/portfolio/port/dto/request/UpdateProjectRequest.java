package com.upendra.portfolio.port.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProjectRequest {

    private String title;

    private String shortDescription;

    private String description;

    private String technologies;

    private String githubUrl;

    private String liveUrl;

    private String imageUrl;

    private Boolean featured;

    private Integer displayOrder;
}