package com.upendra.portfolio.port.dto.media;

import lombok.Data;

@Data
public class UploadResponse {

    private String publicId;
    private String url;
    private String secureUrl;
    private String resourceType;
    private Long size;
    private String originalFilename;
}