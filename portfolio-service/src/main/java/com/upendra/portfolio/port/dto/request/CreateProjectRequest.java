package com.upendra.portfolio.port.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateProjectRequest {

    @NotBlank
    private String title;

    private String shortDescription;

    private String description;

    @NotBlank
    private String technologies;

    private String githubUrl;

    private String liveUrl;

    private String imageUrl;

    @NotNull
    private Boolean featured;

    @NotNull
    private Integer displayOrder;
}