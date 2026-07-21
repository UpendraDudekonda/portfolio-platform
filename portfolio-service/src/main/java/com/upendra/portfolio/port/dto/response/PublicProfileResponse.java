package com.upendra.portfolio.port.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;


@Schema(description = "Public Profile Response")
@Data
@Builder
public class PublicProfileResponse {

    private String fullName;

    private String headline;

    private String about;

    private String location;

    private String profileImageUrl;

    private String resumeUrl;
}
