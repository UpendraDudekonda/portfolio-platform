package com.upendra.portfolio.port.dto.request;

import org.hibernate.validator.constraints.URL;

import com.upendra.portfolio.port.enums.SocialPlatform;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSocialLinkRequest {

    @NotNull
    private SocialPlatform platform;

    @NotBlank
    @URL
    private String url;

    @NotNull
    private Integer displayOrder;

}

