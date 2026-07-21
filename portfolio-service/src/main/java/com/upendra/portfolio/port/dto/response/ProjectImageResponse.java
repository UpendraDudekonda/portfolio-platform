package com.upendra.portfolio.port.dto.response;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectImageResponse {

    private Long id;

    private String imageUrl;

    private Integer displayOrder;

    private LocalDateTime createdAt;
}